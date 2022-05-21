package com.example.setupmagement.web.controller;

import com.example.setupmagement.config.Constants;
import com.example.setupmagement.service.LocationService;
import com.example.setupmagement.web.model.LocationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("get all available locations")
    public void givenLocations_whenGetLocations_thenReturn200() throws Exception {
        createTestLocation("test location 1");
        createTestLocation("test location 2");
        this.mockMvc
                .perform(get(Constants.API_V1_BASE_PATH + "/locations").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.[0].name", is("test location 1")))
                .andExpect(jsonPath("$.[0].longitude", is(-1D)))
                .andExpect(jsonPath("$.[0].latitude", is(1D)))
                .andExpect(jsonPath("$.[1].name", is("test location 2")));
    }

    @Test
    @DisplayName("create new location")
    public void givenLocation_whenCreation_thenReturn201() throws Exception {
        LocationDTO location = LocationDTO.builder()
                .name("test")
                .longitude(-1D)
                .latitude(1D)
                .build();

        String jsonData = mapper.writeValueAsString(location);

        this.mockMvc.perform(post(Constants.API_V1_BASE_PATH + "/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrlPattern("**/" + Constants.API_V1_BASE_PATH + "/location/{\\d+}"))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.longitude", is(-1D)))
                .andExpect(jsonPath("$.latitude", is(1D)));
    }

    @Test
    @DisplayName("get single location")
    public void givenLocation_whenGetById_thenReturn200() throws Exception {
        LocationDTO dto = createTestLocation("test");

        this.mockMvc.perform(get(Constants.API_V1_BASE_PATH + "/location/{id}", dto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.longitude", is(-1D)))
                .andExpect(jsonPath("$.latitude", is(1D)));
    }

    private LocationDTO createTestLocation(String name) {
        LocationDTO location = LocationDTO.builder()
                .name(name)
                .longitude(-1D)
                .latitude(1D)
                .build();
        return locationService.saveLocation(location);
    }

}
