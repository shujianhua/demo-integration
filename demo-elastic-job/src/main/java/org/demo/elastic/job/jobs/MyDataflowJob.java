package org.demo.elastic.job.jobs;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.shu.elasticjob.spring.boot.annotation.ElasticJobLite;

//@ElasticJobLite(jobType = JobType.DATAFLOW, cron = "0/10 * * * * ?", jobName = "myDataflowJob", 
//shardingTotalCount = 2, shardingItemParameters = "0=A,1=B", listenerName = "myDataflowJobListener")
public class MyDataflowJob implements DataflowJob<Student> {
	private static final Logger log = LoggerFactory.getLogger(MyDataflowJob.class);

	@Override
	public List<Student> fetchData(ShardingContext paramShardingContext) {
		List<Student> list = new ArrayList<Student>();
		Student s = new Student();
		s.setUserName("zhangshan");
		list.add(s);
		return list;
	}

	@Override
	public void processData(ShardingContext paramShardingContext, List<Student> paramList) {
		for (Student s: paramList) {
			log.info(s.getUserName());
		}
	}
}
