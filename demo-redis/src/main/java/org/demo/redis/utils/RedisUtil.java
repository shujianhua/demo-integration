package org.demo.redis.utils;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

/**
 * Redis 锁操作工具类
 * @author ShuJianhua
 */

@Component
public class RedisUtil {
	private final static Logger log = LoggerFactory.getLogger(RedisUtil.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private StringRedisTemplate stringRedisTemplateDemo;

	private DefaultRedisScript<String> getLockRedisScript;
	private DefaultRedisScript<String> releaseLockRedisScript;

	private StringRedisSerializer argsStringSerializer = new StringRedisSerializer();
	private StringRedisSerializer resultStringSerializer = new StringRedisSerializer();

	private final String EXEC_RESULT = "1";

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		getLockRedisScript = new DefaultRedisScript<String>();
		getLockRedisScript.setResultType(String.class);
		releaseLockRedisScript = new DefaultRedisScript<String>();
		releaseLockRedisScript.setResultType(String.class);

		// 初始化装载 lua 脚本
		getLockRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/getLock.lua")));
		releaseLockRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/releaseLock.lua")));

	}

	/**
	 * 加锁操作
	 * @param key Redis 锁的 key 值
	 * @param requestId 请求id，防止解了不该由自己解的锁 (随机生成)
	 * @param expireTime 锁的超时时间(毫秒)
	 * @param retryTimes 获取锁的重试次数
	 * @return true or false
	 */

	@SuppressWarnings("unchecked")
	public boolean lock(String key, String requestId, String expireTime, int retryTimes) {
		if (retryTimes <= 0)
			retryTimes = 1;

		try {
			int count = 0;
			while (true) {
				String result = stringRedisTemplateDemo.execute(getLockRedisScript, argsStringSerializer, resultStringSerializer,
						Collections.singletonList(key), requestId, expireTime);
				log.debug("result:{},type:{}", result, result.getClass().getName());
				if (EXEC_RESULT.equals(result)) {
					return true;
				} else {
					count++;
					if (retryTimes == count) {
						log.warn("has tried {} times , failed to acquire lock for key:{},requestId:{}", count, key, requestId);
						return false;
					} else {
						log.warn("try to acquire lock {} times for key:{},requestId:{}", count, key, requestId);
						Thread.sleep(100);
						continue;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 解锁操作
	 * @param key Redis 锁的 key 值
	 * @param requestId 请求 id, 防止解了不该由自己解的锁 (随机生成)
	 * @return true or false
	 */
	@SuppressWarnings("unchecked")
	public boolean unLock(String key, String requestId) {
		String result = stringRedisTemplateDemo.execute(releaseLockRedisScript, argsStringSerializer, resultStringSerializer,
				Collections.singletonList(key), requestId);
		if (EXEC_RESULT.equals(result)) {
			return true;
		}
		return false;
	}

}
