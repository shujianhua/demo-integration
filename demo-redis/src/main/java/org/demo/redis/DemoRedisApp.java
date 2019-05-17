package org.demo.redis;

import java.io.IOException;
import java.net.UnknownHostException;

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

		log.debug("This is the debug info!");
		
		log.info("Args：");
		for (String s : args) {
			log.info("{}", s);
		}

		try {
			while (true) {
				System.in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
