package org.demo.redis;

import java.net.UnknownHostException;

import org.demo.redis.service.IDemoRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

@EnableConfigurationProperties
@ImportResource({ "classpath:applicationContext.xml" })
@SpringBootApplication
public class DemoRedisApp {

	private static final Logger log = LoggerFactory.getLogger(DemoRedisApp.class);

	public static void main(String[] args) throws UnknownHostException {

		ConfigurableApplicationContext context = SpringApplication.run(DemoRedisApp.class, args);
		Environment env = context.getEnvironment();
		log.info("Application name is {}", env.getProperty("spring.application.name"));

		IDemoRedisService demoService = (IDemoRedisService) context.getBean("demoRedisServiceImpl");

//		demoService.getOneLock(); //测试加锁
//		demoService.releaseOneLock(); //测试解锁
		
		demoService.testLockAndUnlock(); //测试多线程竞争锁场景

	}
}
