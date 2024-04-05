package com.example.weatherapplicationproject.entity;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.*;


import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "pipeline_instance")
public class PipelineInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Pipeline.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pipeline_id", foreignKey = @ForeignKey(name = "pipeline_id_FK"), referencedColumnName = "id")
    private Pipeline pipeline;

    @Column(name = "config")
    @JdbcTypeCode(SqlTypes.JSON)
    private String config;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "synced")
    private Boolean synced;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "webhook_config")
    @JdbcTypeCode(SqlTypes.JSON)
    private String webhookConfig;

    @Column(name = "quartz_schedule")
    private String quartzSchedule;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = PipelineRun.class, mappedBy = "pipelineInstance", fetch = FetchType.LAZY)
    private List<PipelineRun> pipelineRun = new LinkedList<PipelineRun>();

}

