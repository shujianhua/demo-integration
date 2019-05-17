package org.demo.test;

import org.demo.redis.DemoRedisApp;
import org.demo.redis.service.IDemoRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DemoRedisApp.class })
@ActiveProfiles("dev")
public class RedisApplicationTests {
	private final static Logger log = LoggerFactory.getLogger(RedisApplicationTests.class);

	@Autowired
	ApplicationContext context;
	@Autowired
	IDemoRedisService demoService;

	@Test
	public void appTest() {
		Environment env = context.getEnvironment();
		log.info("Application name is {}", env.getProperty("spring.application.name"));

		 demoService.getOneLock(); //测试加锁
		// demoService.releaseOneLock(); //测试解锁

//		demoService.testLockAndUnlock(); // 测试多线程竞争锁场景

	}

}
