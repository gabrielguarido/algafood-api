package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.CityRequest;
import com.algaworks.algafood.api.model.CityResponse;
import com.algaworks.algafood.api.transformer.CityTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private static final String CITY_IN_USE_EXCEPTION_MESSAGE = "The city ID %s is currently being used and cannot be removed";

    private final CityRepository cityRepository;

    private final StateService stateService;

    private final CityTransformer cityTransformer;

    @Autowired
    public CityService(CityRepository cityRepository, StateService stateService, CityTransformer cityTransformer) {
        this.cityRepository = cityRepository;
        this.stateService = stateService;
        this.cityTransformer = cityTransformer;
    }

    public List<CityResponse> list() {
        return cityTransformer.toResponse(cityRepository.findAll());
    }

    public CityResponse find(Long id) {
        return cityTransformer.toResponse(verifyIfExists(id));
    }

    @Transactional
    public CityResponse save(CityRequest cityRequest) {
        var city = cityTransformer.toEntity(cityRequest);

        validateState(city.getState().getId());

        return cityTransformer.toResponse(cityRepository.save(city));
    }

    @Transactional
    public CityResponse update(Long id, CityRequest cityRequest) {
        try {
            var existingCity = verifyIfExists(id);

            cityTransformer.copyPropertiesToEntity(cityRequest, existingCity);

            validateState(existingCity.getState().getId());

            return cityTransformer.toResponse(cityRepository.save(existingCity));
        } catch (CityNotFoundException | StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
            cityRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(CITY_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        }
    }

    public City verifyIfExists(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    private void validateState(Long stateId) {
        try {
            stateService.find(stateId);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
