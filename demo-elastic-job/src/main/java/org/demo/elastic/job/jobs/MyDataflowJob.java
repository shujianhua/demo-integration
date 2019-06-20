package org.demo.elastic.job.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.shu.elasticjob.spring.boot.annotation.ElasticJobLite;

//@ElasticJobLite(jobType = JobType.DATAFLOW, cron = "0/10 * * * * ?", jobName = "myDataflowJob", 
//shardingTotalCount = 2, shardingItemParameters = "0=A,1=B", listenerName = "MyDataflowJobListener")
public class MyDataflowJob implements DataflowJob<String> {
	private static final Logger log = LoggerFactory.getLogger(MyDataflowJob.class);

	@Override
	public List<String> fetchData(ShardingContext paramShardingContext) {
		List<String> list = new ArrayList<String>();
		String s = UUID.randomUUID().toString();
		list.add(s);
		log.info("ShardingItem: {}, fetchData->{},ThreadName:{}", paramShardingContext.getShardingItem(), s,Thread.currentThread().getName());
		return list;
	}

	@Override
	public void processData(ShardingContext paramShardingContext, List<String> paramList) {
		for (String s: paramList) {
			log.info("ShardingItem: {}, processData->{},ThreadName:{}", paramShardingContext.getShardingItem(), s ,Thread.currentThread().getName());
		}
	}

}
