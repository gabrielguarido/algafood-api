package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.StateRequest;
import com.algaworks.algafood.api.model.StateResponse;
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

        return stateTransformer.toResponse(stateRepository.save(state));
    }

    @Transactional
    public StateResponse update(Long id, StateRequest stateRequest) {
        try {
            var existingState = verifyIfExists(id);

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
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(STATE_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);
        }
    }

    private State verifyIfExists(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new StateNotFoundException(id));
    }
}
