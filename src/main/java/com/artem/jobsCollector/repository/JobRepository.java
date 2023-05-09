package com.artem.jobsCollector.repository;

import com.artem.jobsCollector.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Job j WHERE j.slug IN :slugs")
    void deleteAllBySlugs(@Param("slugs") List<String> slugs);

    default void deleteAllBySlugsSafe(List<String> slugs) {
        if (!slugs.isEmpty()) {
            deleteAllBySlugs(slugs);
        }
    }
}
