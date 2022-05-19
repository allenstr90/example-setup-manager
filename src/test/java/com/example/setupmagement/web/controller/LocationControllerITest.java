package com.example.setupmagement.web.controller;

import com.example.setupmagement.service.LocationService;
import com.example.setupmagement.web.model.LocationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class LocationControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationService locationService;


    @Test
    @DisplayName("get all available locations")
    public void givenLocations_whenGetLocations_thenReturn200() throws Exception {
        createTestLocation("test location 1");
        createTestLocation("test location 2");
        this.mockMvc
                .perform(get("/api/v1/locations").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.[0].name", is("test location 1")))
                .andExpect(jsonPath("$.[1].name", is("test location 2")));
    }

    private void createTestLocation(String name) {
        LocationDTO location = LocationDTO.builder().name(name).build();
        locationService.saveLocation(location);
    }

}
