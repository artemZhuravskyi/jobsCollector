package com.artem.jobsCollector.collector;

import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.generatedClass.Datum;
import com.artem.jobsCollector.generatedClass.JobsInfo;
import com.artem.jobsCollector.mapper.JobMapper;
import com.artem.jobsCollector.repository.JobRepository;
import com.artem.jobsCollector.service.job.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JobCollector {

    private static final String ARBEITNOW_API_PAGE = "https://www.arbeitnow.com/api/job-board-api/?page=";
    private static final String DATABASE_SAVED = "Database is saved";

    @Value("${data.page-quantity}")
    private int PAGE_QUANTITY;

    private final Logger logger = LoggerFactory.getLogger(JobCollector.class);

    private final JobService jobService;
    private final JobMapper jobMapper;
    private final ObjectMapper objectMapper;
    private final OkHttpClient client;
    private final JobRepository jobRepository;

    @Transactional
    public void collectJobs() {
        List<String> slugsFromDbToDelete = new ArrayList<>(jobService.findAllSlugs());

        List<Datum> jobsDTO = getJobsDTOFromAPI();

        List<Job> jobsToSave = jobMapper.jobsDTOToJobs(jobsDTO);
        List<String> jobSlugs = jobsToSave.stream().map(Job::getSlug).toList();

        deleteDeprecatedJobs(slugsFromDbToDelete, jobSlugs);

        jobService.saveAll(jobsToSave);

        logger.info(DATABASE_SAVED);
    }

    private void deleteDeprecatedJobs(List<String> slugsFromDbToDelete, List<String> jobSlugs) {
        slugsFromDbToDelete.removeAll(jobSlugs);
        jobService.deleteBySlugs(slugsFromDbToDelete);
    }

    private List<Datum> getJobsDTOFromAPI() {
        List<Datum> datum = new ArrayList<>();

        for (int i = 1; i <= PAGE_QUANTITY; i++) {

            Request request = new Request.Builder()
                    .url(ARBEITNOW_API_PAGE + i)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String jsonString = response.body().string();
                JobsInfo jobsInfo = objectMapper.readValue(jsonString, JobsInfo.class);
                datum.addAll(jobsInfo.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return datum;
    }

}
