package com.artem.jobsCollector.repository;

import com.artem.jobsCollector.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("SELECT slug FROM Job")
    List<String> findAllJobSlugs();

    void deleteBySlug(String slug);
}
