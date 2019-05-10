package org.demo.redis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.demo.redis.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("demoRedisServiceImpl")
public class DemoRedisServiceImpl implements IDemoRedisService {
	private final static Logger log = LoggerFactory.getLogger(DemoRedisServiceImpl.class);

	@Autowired
	RedisUtil redisUtil;

	@Override
	public void getOneLock() {
		String key = "key1234";
		String requestId = "abc123";
		String expireTime = "100000";
		int retryTimes = 3;

		if (redisUtil.lock(key, requestId, expireTime, retryTimes)) {
			log.info("获取锁成功key:{},requestId:{}", key, requestId);
		} else {
			log.info("获取锁失败key:{},requestId:{}", key, requestId);
		}
	}

	@Override
	public void releaseOneLock() {
		String key = "key1234";
		String requestId = "abc123";
		if (redisUtil.unLock(key, requestId)) {
			log.info("释放锁成功。key:{},requestId:{}.", key, requestId);
		} else {
			log.info("释放锁失败。key:{},requestId:{}.", key, requestId);
		}
	}

	/**
	 * 测试多线程竞争锁场景
	 * 100 个线程同时修改一个静态常量值 NUM（初始值为0）
	 * 每个线程加1，最终结果 100 
	 * （如果不加锁不能确定结果是100）
	 */
	@Override
	public void testLockAndUnlock() {
		String key = "prefix_key123456"; // redis 锁的 key 值
		String expireTime = "5000";// 锁的超时时间(毫秒)，评估任务时间，建议任务的时间不要太长
		int retryTimes = 3;// 获取锁的重试次数

		NUM = 0;// 共享变量
		int threasCount = 100;// 线程任务个数

		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < threasCount; i++) {
			list.add(new Thread(new Runnable() {
				@Override
				public void run() {

					// request id, 防止解了不该由自己解的锁。
					String requestId = UUID.randomUUID().toString();
					while (true) { //这里循环操作，以确保该线程一定获得锁并执行线程任务
						
						if (redisUtil.lock(key, requestId, expireTime, retryTimes)) {
							try {
								// 调用业务逻辑
								doSomething();
							} finally {
								redisUtil.unLock(key, requestId);
							}
							break;
						}
					}

				}
			}));
		}

		//启动所有任务线程
		for (Thread t : list) {
			t.start();
		}

		//轮询状态，等待所有子线程完成
		while (true) {
			int aliveThreadCount = 0;
			for (Thread t : list) {
				if (t.isAlive()) {
					++aliveThreadCount;
				}
			}

			if (aliveThreadCount == 0) {
				log.debug("All Threads are completed!");
				break;
			} else {
				log.debug("Threads have not yet completed， sleep 5s!");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		//打印最终结果值： NUM
		log.info("Completed! The final value of NUM is : {}", NUM);
	}

	
	private static int NUM = 0; // 所有任务共享变量，要修改的值

	/**
	 * 模拟业务逻辑处理：NUM +1 操作
	 */
	private void doSomething() {

		try {
			Thread.sleep(Double.valueOf(2000D * Math.random()).intValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int tmp = NUM;
		tmp = tmp + 1;
		NUM = tmp;
	}

}
