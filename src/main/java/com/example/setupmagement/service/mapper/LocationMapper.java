package com.example.setupmagement.service.mapper;

import com.example.setupmagement.entity.Location;
import com.example.setupmagement.web.model.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(source = "entity.location.longitude", target = "longitude")
    @Mapping(source = "entity.location.latitude", target = "latitude")
    LocationDTO toDto(Location entity);

    @Mapping(source = "dto.longitude", target = "location.longitude")
    @Mapping(source = "dto.latitude", target = "location.latitude")
    Location toEntity(LocationDTO dto);
}
