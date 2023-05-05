package com.artem.jobsCollector.service.job;

import com.artem.jobsCollector.entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {

    void saveAll(List<Job> jobs);

    List<Job> findAllSorted(Optional<Integer> page, Optional<String> sortBy);

    List<String> findAllSlugs();

    void deleteAllBySlugs(List<String> slugs);

}
