package org.demo.elastic.job.jobs;

import com.dangdang.ddframe.job.api.script.ScriptJob;

//@ElasticJobLite(jobType = JobType.SCRIPT, cron = "0/5 * * * * ?", jobName = "myScriptJob", scriptCommandLine = "D:/tmp/2/script.sh",
//shardingTotalCount = 3, shardingItemParameters = "0=B,1=S,2=G", listenerName = "MyScriptJobListener")
//@ElasticJobLite(jobType = JobType.SCRIPT, cron = "0/10 * * * * ?", jobName = "myScriptJob", scriptCommandLine = "D:/tmp/2/script.bat")
public class MyScriptJob implements ScriptJob {
}
