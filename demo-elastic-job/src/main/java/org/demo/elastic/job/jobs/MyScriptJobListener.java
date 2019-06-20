
package org.demo.elastic.job.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

@Component("MyScriptJobListener")
public class MyScriptJobListener implements ElasticJobListener {
	private static final Logger log = LoggerFactory.getLogger(MyScriptJobListener.class);
	
	@Override
	public void afterJobExecuted(ShardingContexts arg0) {
		log.info("MyScriptJobListener---afterJobExecuted-----{}, taskId:{}",Thread.currentThread().getName(),arg0.getTaskId());
	}

	@Override
	public void beforeJobExecuted(ShardingContexts arg0) {
		log.info("MyScriptJobListener---beforeJobExecuted-----{}",Thread.currentThread().getName());
	}

}
