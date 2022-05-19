package com.example.setupmagement.service.mapper;

import com.example.setupmagement.entity.Location;
import com.example.setupmagement.web.model.LocationDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

}
