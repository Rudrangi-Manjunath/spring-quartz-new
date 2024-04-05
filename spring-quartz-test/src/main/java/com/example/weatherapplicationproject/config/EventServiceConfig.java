package com.example.weatherapplicationproject.config;

import com.example.weatherapplicationproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class EventServiceConfig implements ApplicationRunner {

    @Autowired
    private EventService eventService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        eventService.scheduleEvent();
        System.out.println("EventServiceConfig.run");
    }
}
