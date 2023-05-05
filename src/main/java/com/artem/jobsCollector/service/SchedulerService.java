package com.artem.jobsCollector.service;

import com.artem.jobsCollector.collector.JobCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private static final long SCHEDULE_MINUTES = 20L;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final JobCollector jobCollector;

    public void scheduleCollectingJobs() {
        threadPoolTaskScheduler.scheduleWithFixedDelay(jobCollector::collectJobs, Duration.ofMinutes(SCHEDULE_MINUTES));
    }
}
