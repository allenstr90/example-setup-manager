package com.example.setupmagement.service;

import com.example.setupmagement.web.model.LocationDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    private LocationDTO testLocation;

    @BeforeEach
    private void init() {
        this.testLocation = LocationDTO.builder()
                .name("test location")
                .latitude(-1D)
                .longitude(1D)
                .build();
    }

    @Test
    @DisplayName("create new location")
    @Transactional
    public void givenNewLocation_whenBeCreated_thenReturnsItWithId() {
        LocationDTO locationDTO = locationService.saveLocation(testLocation);
        assertEquals("test location", locationDTO.getName());
        assertNotNull(locationDTO.getId());
        assertNotEquals(0L, locationDTO.getId());
    }

    @Test
    @DisplayName("get all locations")
    @Transactional
    public void givenLocation_whenGetAll_thenReturnAll() {
        locationService.saveLocation(testLocation);

        List<LocationDTO> allLocations = locationService.getAllLocations();
        assertThat(allLocations).isNotNull();
        assertThat(allLocations).isNotEmpty();
        assertThat(allLocations)
                .filteredOn(locationDTO -> locationDTO.getName().equals("test location"))
                .isNotEmpty();
    }

    @Test
    @DisplayName("get location by id")
    @Transactional
    public void givenLocation_whenGetId_thenReturnLocation() {
        LocationDTO saveLocation = locationService.saveLocation(testLocation);

        LocationDTO foundLocation = locationService.getLocationById(saveLocation.getId());

        assertThat(foundLocation).isNotNull();
        assertThat(foundLocation).isEqualTo(saveLocation);
        assertThat(foundLocation.getName()).isEqualTo(saveLocation.getName());
    }
}
