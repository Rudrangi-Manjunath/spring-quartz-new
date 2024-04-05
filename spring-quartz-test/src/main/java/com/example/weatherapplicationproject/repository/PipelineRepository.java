package com.example.weatherapplicationproject.repository;

import com.example.weatherapplicationproject.entity.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PipelineRepository extends JpaRepository<Pipeline, Long> {

    Optional<Pipeline> findByName(String pipelineName);

}
