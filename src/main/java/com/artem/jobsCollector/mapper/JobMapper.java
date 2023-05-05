package com.artem.jobsCollector.mapper;

import com.artem.jobsCollector.entity.Company;
import com.artem.jobsCollector.entity.Job;
import com.artem.jobsCollector.entity.Location;
import com.artem.jobsCollector.generatedClass.Datum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {LocationMapper.class, CompanyMapper.class})
public interface JobMapper {

    @Mapping(target = "location", source = "location")
    Job jobDTOToJob(Datum jobDTO, Company company, Location location);

    default List<Job> jobsDTOToJobs(List<Datum> jobsDTO) {
        List<Job> jobs = new ArrayList<>();
        List<Company> companies = jobsDTOToCompanies(jobsDTO);
        List<Location> locations = jobsDTOToLocations(jobsDTO);

        jobsDTO.forEach(jobDTO -> {
            Company company = companies.stream().filter(comp -> isEquals(jobDTO, comp)).findFirst().get();
            Location location = locations.stream().filter(loc -> isEquals(jobDTO, loc)).findFirst().get();
            jobs.add(jobDTOToJob(jobDTO, company, location));
        });

        return jobs;
    }

    private boolean isEquals(Datum jobDTO, Location loc) {
        return jobDTO.getLocation().equals(loc.getCity());
    }

    private boolean isEquals(Datum jobDTO, Company comp) {
        return jobDTO.getCompanyName().equals(comp.getName());
    }

    private List<Location> jobsDTOToLocations(List<Datum> jobsDTO) {
        return jobsDTO.stream()
                .map(Datum::getLocation)
                .distinct()
                .map(Location::new)
                .toList();
    }

    private List<Company> jobsDTOToCompanies(List<Datum> jobsDTO) {
        return jobsDTO.stream()
                .map(Datum::getCompanyName)
                .distinct()
                .map(Company::new)
                .toList();
    }

}
