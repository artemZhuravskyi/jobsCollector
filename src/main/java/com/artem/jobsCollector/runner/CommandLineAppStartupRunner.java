package com.artem.jobsCollector.runner;

import com.artem.jobsCollector.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final SchedulerService schedulerService;

    @Override
    public void run(String... args) {
        schedulerService.scheduleCollectingJobs();
    }
}
