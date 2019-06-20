package org.demo.elastic.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@EnableConfigurationProperties
@SpringBootApplication
public class DemoElasticJobApp {
	private static final Logger log = LoggerFactory.getLogger(DemoElasticJobApp.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoElasticJobApp.class, args);
		Environment env = context.getEnvironment();
		log.info("Application name is {}", env.getProperty("spring.application.name"));
	}
}
