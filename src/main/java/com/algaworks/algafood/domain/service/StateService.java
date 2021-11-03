package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.StateRequest;
import com.algaworks.algafood.api.model.response.StateResponse;
import com.algaworks.algafood.api.transformer.StateTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    private static final String STATE_IN_USE_EXCEPTION_MESSAGE = "The state ID %s is currently being used and cannot be removed";
    private static final String STATE_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The state name %s already exists with ID %s";

    private final StateRepository stateRepository;

    private final StateTransformer stateTransformer;

    @Autowired
    public StateService(StateRepository stateRepository, StateTransformer stateTransformer) {
        this.stateRepository = stateRepository;
        this.stateTransformer = stateTransformer;
    }

    public List<StateResponse> list() {
        return stateTransformer.toResponse(stateRepository.findAll());
    }

    public StateResponse find(Long id) {
        return stateTransformer.toResponse(verifyIfExists(id));
    }

    @Transactional
    public StateResponse save(StateRequest stateRequest) {
        var state = stateTransformer.toEntity(stateRequest);

        validateName(state.getName());

        return stateTransformer.toResponse(stateRepository.save(state));
    }

    @Transactional
    public StateResponse update(Long id, StateRequest stateRequest) {
        try {
            var existingState = verifyIfExists(id);

            validateName(stateRequest.getName());

            stateTransformer.copyPropertiesToEntity(stateRequest, existingState);

            return stateTransformer.toResponse(stateRepository.save(existingState));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            stateRepository.deleteById(id);
            stateRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(String.format(STATE_IN_USE_EXCEPTION_MESSAGE, id));
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);
        }
    }

    private State verifyIfExists(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new StateNotFoundException(id));
    }

    private void validateName(String name) {
        var state = stateRepository.findByName(name);

        if (state.isPresent()) {
            throw new BusinessException(String.format(STATE_ALREADY_EXISTS_EXCEPTION_MESSAGE, name, state.get().getId()));
        }
    }
}
