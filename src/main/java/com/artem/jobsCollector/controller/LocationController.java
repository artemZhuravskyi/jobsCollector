package com.artem.jobsCollector.controller;

import com.artem.jobsCollector.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/statistics")
    public Map<String, Integer> groupJobsByLocation() {
        return locationService.groupJobsByLocation();
    }
}
