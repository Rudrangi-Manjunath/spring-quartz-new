package com.example.weatherapplicationproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PipelineInstanceDTO {

    @JsonProperty("pipeline_name")
    @NotEmpty(message = "Pipeline name is required")
    private String pipelineName;

    @JsonProperty("pipeline_instance_name")
    @NotEmpty(message = "Pipeline instance name is required")
    private String pipelineInstanceName;

    @JsonProperty("config")
    private Map<String,Object> config;

    @JsonProperty("schedule")
    private String schedule;

    @JsonProperty("quartz_schedule")
    @NotEmpty(message = "Quartz schedule is required")
    private String quartzSchedule;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("webhook_config")
    private Map<String,Object> webhookConfig;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

}
