package com.example.weatherapplicationproject.service;

import com.example.weatherapplicationproject.entity.PipelineInstance;
import org.quartz.SchedulerException;

public interface EventService {
    void scheduleEvent() throws SchedulerException;

    void scheduleInstance(PipelineInstance pipelineInstance) throws SchedulerException;
}
