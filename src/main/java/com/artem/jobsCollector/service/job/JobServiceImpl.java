package com.artem.jobsCollector.service.job;

import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private static final int PAGE_SIZE = 25;
    private static final int TOP_JOBS_PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 0;

    private final JobRepository jobRepository;

    @Override
    public void saveAll(List<Job> jobs) {
        jobRepository.saveAll(jobs);
    }

    @Override
    public List<Job> findAllSorted(Optional<Integer> page, Optional<String> sortBy) {
        if (page.isPresent()) {
            page = Optional.of(page.get() - 1);
        }
        List<Job> sortedJobs = jobRepository.findAll(PageRequest.of(page.orElse(FIRST_PAGE), PAGE_SIZE,
                Sort.Direction.DESC, sortBy.orElse("createdAt"))).getContent();
        sortedJobs.forEach(job -> job.setViews(job.getViews() + 1));
        saveAll(sortedJobs);
        return sortedJobs;
    }
    public List<Job> findTop10ByViews() {
        return jobRepository.findAll(PageRequest.of(FIRST_PAGE, TOP_JOBS_PAGE_SIZE,
                Sort.Direction.DESC, "views")).getContent();
    }

    @Override
    public void deleteAllBySlugs(List<String> slugs) {
        jobRepository.deleteAllBySlugsSafe(slugs);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }
}
