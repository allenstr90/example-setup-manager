package com.example.setupmagement.web.controller;

import com.example.setupmagement.config.Constants;
import com.example.setupmagement.service.LocationService;
import com.example.setupmagement.web.model.LocationDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(Constants.API_V1_BASE_PATH)
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @PostMapping(value = "/location", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> createNewLocation(@RequestBody LocationDTO locationDTO, UriComponentsBuilder builder) {
        LocationDTO saveLocation = locationService.saveLocation(locationDTO);
        return ResponseEntity
                .created(builder
                        .path(Constants.API_V1_BASE_PATH + "/location/{id}")
                        .buildAndExpand(saveLocation.getId())
                        .toUri())
                .body(saveLocation);
    }

    @GetMapping(value = "/location/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable("id") Long id){
        return ResponseEntity.ok(locationService.getLocationById(id));
    }
}
