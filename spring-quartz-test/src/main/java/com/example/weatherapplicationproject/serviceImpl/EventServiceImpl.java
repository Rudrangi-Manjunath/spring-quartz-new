package com.example.weatherapplicationproject.serviceImpl;

import com.example.weatherapplicationproject.entity.PipelineInstance;
import com.example.weatherapplicationproject.helper.EventHelper;
import com.example.weatherapplicationproject.repository.PipelineInstanceRepository;
import com.example.weatherapplicationproject.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EventServiceImpl implements EventService {

    public PipelineInstanceRepository pipelineInstanceRepository;
    public SchedulerFactoryBean factory;
    public Scheduler scheduler;

    @Autowired
    public EventServiceImpl(PipelineInstanceRepository pipelineInstanceRepository, SchedulerFactoryBean schedulerFactoryBean) {
        this.pipelineInstanceRepository = pipelineInstanceRepository;
        this.factory = schedulerFactoryBean;

        scheduler = factory.getScheduler();

        try{
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void scheduleEvent() {
        Iterable<PipelineInstance> pipelineInstances = pipelineInstanceRepository.findByQuartzScheduleNotNullAndIsActiveTrue();
        for (PipelineInstance pipelineInstance : pipelineInstances) {
            try {
                scheduleInstance(pipelineInstance);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void scheduleInstance(PipelineInstance pipelineInstance) throws SchedulerException {
        JobDetail jobDetail = EventHelper.buildJobDetail(pipelineInstance);
        CronTrigger cronTrigger = EventHelper.buildJobTrigger(jobDetail, pipelineInstance);

        if(scheduler.checkExists(jobDetail.getKey())){
            try {
                scheduler.deleteJob(jobDetail.getKey());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Scheduling job with name: " + jobDetail.getKey().getName() + " and group: " + jobDetail.getKey().getGroup() + " and cron expression: " + cronTrigger.getCronExpression());
        log.info("Scheduling job with name: " + jobDetail.getKey().getName() + " and group: " + jobDetail.getKey().getGroup() + " and cron expression: " + cronTrigger.getCronExpression());

        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }


}
