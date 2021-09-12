package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private static final String CITY_IN_USE_EXCEPTION_MESSAGE = "The city ID %s is currently being used and cannot be removed";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    public List<City> list() {
        return cityRepository.findAll();
    }

    public City find(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    @Transactional
    public City save(City city) {
        try {
            stateService.find(city.getState().getId());
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return cityRepository.save(city);
    }

    @Transactional
    public City update(Long id, City city) {
        try {
            var existingCity = find(id);

            BeanUtils.copyProperties(city, existingCity, "id");

            return save(existingCity);
        } catch (CityNotFoundException | StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(CITY_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        }
    }
}
