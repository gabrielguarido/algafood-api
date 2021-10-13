package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> list() {
        return stateRepository.findAll();
    }

    public State find(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new StateNotFoundException(id));
    }

    @Transactional
    public State save(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public State update(Long id, State state) {
        try {
            var existingState = find(id);

            BeanUtils.copyProperties(state, existingState, "id");

            return save(existingState);
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
}
