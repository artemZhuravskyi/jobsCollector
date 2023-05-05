package com.artem.jobsCollector.service.location;

import java.util.Map;

public interface LocationService {
    Map<String, Integer> groupJobsByLocation();
}
