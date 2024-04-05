package com.example.weatherapplicationproject.controller;

import com.example.weatherapplicationproject.dto.PipelineInstanceDTO;
import com.example.weatherapplicationproject.entity.Pipeline;
import com.example.weatherapplicationproject.entity.PipelineInstance;
import com.example.weatherapplicationproject.repository.PipelineInstanceRepository;
import com.example.weatherapplicationproject.repository.PipelineRepository;
import com.example.weatherapplicationproject.service.EventService;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/pipelineInstance")
@Slf4j
public class EventServiceController {

    @Autowired
    private PipelineInstanceRepository pipelineInstanceRepository;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<String> createPipelineInstance(@RequestBody PipelineInstanceDTO pipelineInstanceDTO) {
        String pipelineInstanceName = pipelineInstanceDTO.getPipelineInstanceName();

        // Check if pipeline instance with the same name already exists
        Optional<PipelineInstance> existingPipelineInstanceOptional = pipelineInstanceRepository.findByName(pipelineInstanceName);
        if (existingPipelineInstanceOptional.isPresent()) {
            PipelineInstance existingPipelineInstance = existingPipelineInstanceOptional.get();
            pipelineInstanceRepository.delete(existingPipelineInstance);
        }
        else{
            log.info("Pipeline instance with name: " + pipelineInstanceName + " does not exist.");
        }

        // Find pipeline by name
        Pipeline pipeline = pipelineRepository.findByName(pipelineInstanceDTO.getPipelineName().toString())
                .orElseThrow(() -> new EntityNotFoundException("Pipeline not found with name: " + pipelineInstanceDTO.getPipelineName()));

        log.info("Pipeline found: " + pipeline.getId());

        // Map DTO fields to entity
        PipelineInstance pipelineInstance = new PipelineInstance();
        pipelineInstance.setName(pipelineInstanceDTO.getPipelineInstanceName());
        pipelineInstance.setIsActive(pipelineInstanceDTO.getIsActive());
        pipelineInstance.setQuartzSchedule(pipelineInstanceDTO.getQuartzSchedule());
        pipelineInstance.setSchedule(pipelineInstanceDTO.getSchedule());
        pipelineInstance.setCreatedBy("postgres-user");
        pipelineInstance.setLastModifiedBy("postgres-user");
        pipelineInstance.setCreatedDate(java.time.LocalDateTime.now());
        pipelineInstance.setLastModifiedDate(java.time.LocalDateTime.now());

        // Set pipeline
        pipelineInstance.setPipeline(pipeline);

        // Convert config map to JSON
        Gson gson = new Gson();
        String configJson = gson.toJson(pipelineInstanceDTO.getConfig());
        pipelineInstance.setConfig(configJson);

        // Convert webhook config map to JSON
        String webhookConfigJson = gson.toJson(pipelineInstanceDTO.getWebhookConfig());
        pipelineInstance.setWebhookConfig(webhookConfigJson);

        log.info("Pipeline instance created: " + pipelineInstance.toString());

        // Save pipeline instance
        pipelineInstanceRepository.saveAndFlush(pipelineInstance);

        // Schedule instance
        try {
            eventService.scheduleInstance(pipelineInstance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling pipeline instance: " + e.getMessage());
        }

        return ResponseEntity.ok("Pipeline instance created successfully.");
    }

}
