package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.CityRequest;
import com.algaworks.algafood.api.model.CityResponse;
import com.algaworks.algafood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CityTransformer {

    private final ModelMapper mapper;

    @Autowired
    public CityTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CityResponse toResponse(City city) {
        return mapper.map(city, CityResponse.class);
    }

    public List<CityResponse> toResponse(List<City> cityList) {
        return cityList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public City toEntity(CityRequest cityRequest) {
        return mapper.map(cityRequest, City.class);
    }

    public void copyPropertiesToEntity(CityRequest cityRequest, City city) {
        mapper.map(cityRequest, city);
    }
}
