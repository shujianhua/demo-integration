package org.demo.elastic.job.jobs;

import org.demo.elastic.job.jobs.service.MyJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.shu.elasticjob.spring.boot.annotation.ElasticJobLite;

//@ElasticJobLite(cron = "0/5 * * * * ?", jobName = "MySimpleJob", shardingTotalCount = 3, shardingItemParameters = "0=B,1=S,2=G", listenerName = "MySimpleJobListener")
@ElasticJobLite(cron = "0/5 * * * * ?", jobName = "MySimpleJob", listenerName = "MySimpleJobListener")
public class MySimpleJob implements SimpleJob {
	private static final Logger log = LoggerFactory.getLogger(MySimpleJob.class);

	@Autowired
	private MyJobService myJobService;// 调用业务逻辑实现类

	@Override
	public void execute(ShardingContext shardingContext) {
		//log.info("ShardingItem: {}, ShardingParameter: {}", shardingContext.getShardingItem(), shardingContext.getShardingParameter());

		myJobService.doSomething();
	}

}
