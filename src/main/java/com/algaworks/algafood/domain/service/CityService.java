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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

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

    public City save(City city) {
        try {
            stateService.find(city.getState().getId());
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return cityRepository.save(city);
    }

    public City update(Long id, City city) {
        try {
            var existingCity = find(id);

            BeanUtils.copyProperties(city, existingCity, "id");

            return save(existingCity);
        } catch (CityNotFoundException | StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        try {
            cityRepository.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format("The city %s is currently being used and cannot be removed", id)
            );
        } catch (CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
