package com.artem.jobsCollector.service.job;

import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.repository.JobRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
@RunWith(MockitoJUnitRunner.class)
public class JobServiceImplTest {

    private static final int PAGE_SIZE = 25;
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobServiceImpl jobServiceImpl;

    private List<Job> jobs;

    @Before
    public void setUp() {
        jobs = Arrays.asList(new Job(), new Job(), new Job());
    }

    @Test
    public void testFindAllSorted() {

        Optional<Integer> page = Optional.of(1);
        Optional<String> sortBy = Optional.of("createdAt");

        Page<Job> jobPage = new PageImpl<>(jobs);

        when(jobRepository.findAll(any(Pageable.class))).thenReturn(jobPage);

        List<Job> result = jobServiceImpl.findAllSorted(page, sortBy);

        assertEquals(jobs, result);

        verify(jobRepository).findAll(any(Pageable.class));}
}
