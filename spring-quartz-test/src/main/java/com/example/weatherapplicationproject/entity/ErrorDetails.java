package com.example.weatherapplicationproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "error_details")

public class ErrorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PipelineRun.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pipeline_run_id", foreignKey = @ForeignKey(name = "pipeline_run_id_FK"), referencedColumnName = "id")
    private PipelineRun pipelineRun;

    @Column(name = "row")
    private String row;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

}

