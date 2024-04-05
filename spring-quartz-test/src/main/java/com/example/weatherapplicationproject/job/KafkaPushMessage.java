package com.example.weatherapplicationproject.job;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class KafkaPushMessage extends QuartzJobBean {

    @Value("${kafka.topic}")
    private String TOPIC;

    @Value("${kafka.server-endpoint}")
    private String kafkaServerEndpoint;

    @Value("${kafka.jaas-config-osc}")
    private String kafkaJaasConfig_osc;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("KafkaPushMessage job is running");
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String message = jobDataMap.getString("pipeline_instance_name");

        // send the message to the Kafka topic
        try{
//            kafkaTemplate.send(TOPIC, message);
//            log.info("Message sent successfully to topic: " + TOPIC);
        } catch (Exception e) {
            log.error("Error while sending message to Kafka topic: " + e.getMessage());
        }
    }
}
