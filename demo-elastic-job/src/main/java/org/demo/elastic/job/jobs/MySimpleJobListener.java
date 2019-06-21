
package org.demo.elastic.job.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

@Component("mySimpleJobListener")
public class MySimpleJobListener implements ElasticJobListener {
	private static final Logger log = LoggerFactory.getLogger(MySimpleJobListener.class);
	
	@Override
	public void afterJobExecuted(ShardingContexts arg0) {
		log.info("---afterJobExecuted-----{}",Thread.currentThread().getName());
	}

	@Override
	public void beforeJobExecuted(ShardingContexts arg0) {
		log.info("---beforeJobExecuted-----{}",Thread.currentThread().getName());
	}

}
