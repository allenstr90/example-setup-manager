package com.example.setupmagement.service.mapper;

import com.example.setupmagement.entity.Location;
import com.example.setupmagement.web.model.LocationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationMapperTest {


    private LocationMapper locationMapper;

    @Test
    public void givenLocationEntity_shouldBeConvertedToDTO() {
        locationMapper = new LocationMapperImpl();
        Location dbLocation = new Location();
        dbLocation.setId(1L);
        dbLocation.setName("test location");
        LocationDTO locationDTO = locationMapper.toDto(dbLocation);
        assertNotNull(locationDTO);
        assertEquals(dbLocation.getId(), locationDTO.getId());
        assertEquals(dbLocation.getName(), locationDTO.getName());
    }

}
