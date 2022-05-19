package com.example.setupmagement.service;

import com.example.setupmagement.entity.Location;
import com.example.setupmagement.repository.LocationRepository;
import com.example.setupmagement.service.mapper.LocationMapper;
import com.example.setupmagement.web.model.LocationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService {
    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationDTO saveLocation(LocationDTO dto) {
        log.debug("saving location: {}", dto);
        Location entity = locationMapper.toEntity(dto);
        entity = locationRepository.saveAndFlush(entity);
        return locationMapper.toDto(entity);
    }

    public List<LocationDTO> getAllLocations() {
        return locationMapper.toDto(locationRepository.findAll());
    }
}
