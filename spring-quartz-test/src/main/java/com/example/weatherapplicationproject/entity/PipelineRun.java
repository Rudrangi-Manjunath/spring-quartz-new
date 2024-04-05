package com.example.weatherapplicationproject.entity;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.example.weatherapplicationproject.helper.JsonNodeConverter;
import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pipeline_run")

public class PipelineRun {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "dag_run_id")
    private String dagRunId;

    @Column(name = "parent_job_id")
    private Long parentJobId;

    @ManyToOne(targetEntity = PipelineInstance.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "pipeline_instance_id", foreignKey = @ForeignKey(name = "pipeline_instance_id_FK"), referencedColumnName = "id")
    private PipelineInstance pipelineInstance;

    @Column(name = "params")
    @JdbcTypeCode(SqlTypes.JSON)
    private String params;

    @Column(name = "status")
    private String status;

    @Column(name = "retry")
    private Boolean retry;

    @Column(name = "job_start")
    private LocalDateTime jobStart;

    @Column(name = "job_end")
    private LocalDateTime jobEnd;

    @Column(name = "message")
    private String message;

    @Column(name = "source_record_count")
    private Long sourceRecordCount = 0L;

    @Column(name = "processed_record_count")
    private Long processedRecordCount = 0L;

    @Column(name = "error_record_count")
    private Long errorRecordCount = 0L;

    @Column(name = "inserted_record_count")
    private Long insertedRecordCount = 0L;

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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ErrorDetails.class, mappedBy = "pipelineRun", fetch = FetchType.LAZY)
    private List<ErrorDetails> errorDetails = new LinkedList<ErrorDetails>();

}
