package org.demo.elastic.job.jobs;

import org.demo.elastic.job.jobs.service.MyJobService;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.shu.elasticjob.spring.boot.annotation.ElasticJobLite;

@ElasticJobLite(cron = "0/5 * * * * ?", jobName = "MySimpleJob", shardingTotalCount = 3, shardingItemParameters = "0=B,1=S,2=G", listenerName = "mySimpleJobListener")
public class MySimpleJob implements SimpleJob {
	@Autowired
	private MyJobService myJobService;

	@Override
	public void execute(ShardingContext shardingContext) {
		// 调用业务逻辑实现
		myJobService.doSomething();
	}
}