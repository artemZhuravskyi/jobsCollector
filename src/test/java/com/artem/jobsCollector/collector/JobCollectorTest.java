package com.artem.jobsCollector.collector;

import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.mapper.JobMapper;
import com.artem.jobsCollector.service.job.JobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobCollectorTest {

    @InjectMocks
    private JobCollector jobCollector;

    @Mock
    private JobService jobService;

    @Mock
    private JobMapper jobMapper;

    private List<Job> jobs;

    @Before
    public void setUp() {
        jobs = new ArrayList<>();
        Job job1 = new Job();
        Job job2 = new Job();
        jobs.add(job1);
        jobs.add(job2);


        when(jobMapper.jobsDTOToJobs(anyList())).thenReturn(jobs);
    }

    @Test
    public void testCollectJobs() {
        InOrder inOrder = inOrder(jobService);

        jobCollector.collectJobs();

        inOrder.verify(jobService).findAllSlugs();
        inOrder.verify(jobService).deleteAllBySlugs(anyList());
        inOrder.verify(jobService).saveAll(jobs);
    }
}