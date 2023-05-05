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
        return jobRepository.findAll(PageRequest.of(page.orElse(0), PAGE_SIZE,
                Sort.Direction.ASC, sortBy.orElse("createdAt"))).getContent();
    }

    @Override
    public List<String> findAllSlugs() {
        return jobRepository.findAllJobSlugs();
    }

    @Override
    public void deleteAllBySlugs(List<String> slugs) {
        jobRepository.deleteAllBySlugsSafe(slugs);
    }
}
