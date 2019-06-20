package org.demo.elastic.job.jobs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("myJobService")
public class MyJobService {
	private static final Logger log = LoggerFactory.getLogger(MyJobService.class);
	
	public void doSomething(){
		//业务逻辑实现
		log.info("do something here..");
		//
	}

}
