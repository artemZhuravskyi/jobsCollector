package com.artem.jobsCollector.controller;


import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.artem.jobsCollector.entity.Company;
import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.entity.Location;
import com.artem.jobsCollector.service.job.JobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class JobControllerTest {

    @InjectMocks
    private JobController jobController;

    @Mock
    private JobService jobService;

    private Location location;
    private Company company;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        List<Job> jobs = new ArrayList<>();
        company = new Company("ATB");
        location = new Location("Jitomir", jobs);
        jobs.add(new Job("slug 1", company, location, "Job 1", false, 123456, 832));
        jobs.add(new Job("slug 2", company, location, "Job 2", true, 123457, 723));
        when(jobService.findAllSorted(any(Optional.class), any(Optional.class))).thenReturn(jobs);
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    public void testWithoutParamsGetJobs() throws Exception {

        mockMvc.perform(get("/api/v1/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("slug 1"))
                .andExpect(jsonPath("$[0].title").value("Job 1"))
                .andExpect(jsonPath("$[0].remote").value(false))
                .andExpect(jsonPath("$[0].createdAt").value(123456))
                .andExpect(jsonPath("$[0].location.city").value(location.getCity()))
                .andExpect(jsonPath("$[0].company.name").value(company.getName()))
                .andExpect(jsonPath("$[0].views").value(832))

                .andExpect(jsonPath("$[1].slug").value("slug 2"))
                .andExpect(jsonPath("$[1].title").value("Job 2"))
                .andExpect(jsonPath("$[1].remote").value(true))
                .andExpect(jsonPath("$[1].createdAt").value(123457))
                .andExpect(jsonPath("$[1].location.city").value(location.getCity()))
                .andExpect(jsonPath("$[1].company.name").value(company.getName()))
                .andExpect(jsonPath("$[1].views").value(723));
    }

    @Test
    public void testWithParamsGetJobs() throws Exception {

        mockMvc.perform(get("/api/v1/jobs")
                        .param("page", "1")
                        .param("sortBy", "title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("slug 1"))
                .andExpect(jsonPath("$[0].title").value("Job 1"))
                .andExpect(jsonPath("$[0].remote").value(false))
                .andExpect(jsonPath("$[0].createdAt").value(123456))
                .andExpect(jsonPath("$[0].location.city").value(location.getCity()))
                .andExpect(jsonPath("$[0].company.name").value(company.getName()));
    }
}