package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.StateRequest;
import com.algaworks.algafood.api.model.StateResponse;
import com.algaworks.algafood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class StateTransformer {

    private final ModelMapper mapper;

    @Autowired
    public StateTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public StateResponse toResponse(State state) {
        return mapper.map(state, StateResponse.class);
    }

    public List<StateResponse> toResponse(List<State> stateList) {
        return stateList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public State toEntity(StateRequest stateRequest) {
        return mapper.map(stateRequest, State.class);
    }

    public void copyPropertiesToEntity(StateRequest stateRequest, State state) {
        mapper.map(stateRequest, state);
    }
}
