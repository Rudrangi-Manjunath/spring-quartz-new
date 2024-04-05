package com.example.weatherapplicationproject.helper;

import com.example.weatherapplicationproject.entity.PipelineInstance;
import com.example.weatherapplicationproject.job.KafkaPushMessage;
import org.quartz.*;

public class EventHelper {
    public static JobDetail buildJobDetail(PipelineInstance pipelineInstance) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("config", pipelineInstance.getConfig());
        jobDataMap.put("pipeline_instance_id", pipelineInstance.getId());
        jobDataMap.put("pipeline_instance_name", pipelineInstance.getName());


        return JobBuilder.newJob(KafkaPushMessage.class)
                .withIdentity(pipelineInstance.getName(),"kafka-push-jobs-1")
                .withDescription(pipelineInstance.getName())
                .storeDurably()
                .usingJobData(jobDataMap)
                .build();
    }

    public static CronTrigger buildJobTrigger(JobDetail jobDetail, PipelineInstance pipelineInstance){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "kafka-push-triggers-1")
                .withDescription("Kafka Push Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(pipelineInstance.getQuartzSchedule()).withMisfireHandlingInstructionFireAndProceed())
                .build();
    }
}
