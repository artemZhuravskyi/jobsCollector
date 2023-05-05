package com.artem.jobsCollector.service.location;

import com.artem.jobsCollector.entity.Location;
import com.artem.jobsCollector.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Map<String, Integer> groupJobsByLocation() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().collect(Collectors.toMap(Location::getCity, location -> location.getJobs().size()));
    }
}
