package com.artem.jobsCollector.controller;

import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.service.job.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public List<Job> getJobs(@RequestParam Optional<Integer> page,
                             @RequestParam Optional<String> sortBy) {
        return jobService.findAllSorted(page, sortBy);
    }

}
