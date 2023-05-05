package com.artem.jobsCollector.controller;

import com.artem.jobsCollector.service.location.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    public void testGroupJobsByLocation() throws Exception {
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("Berlin", 10);
        expectedResult.put("Kiev", 8);
        expectedResult.put("Chicago", 6);

        when(locationService.groupJobsByLocation()).thenReturn(expectedResult);

        mockMvc.perform(get("/api/v1/locations/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Berlin").value(10))
                .andExpect(jsonPath("$.Kiev").value(8))
                .andExpect(jsonPath("$.Chicago").value(6));

        verify(locationService).groupJobsByLocation();
    }
}