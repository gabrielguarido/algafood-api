package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.UserRequest;
import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserTransformer {

    private final ModelMapper mapper;

    @Autowired
    public UserTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserResponse toResponse(User user) {
        return mapper.map(user, UserResponse.class);
    }

    public List<UserResponse> toResponse(List<User> userList) {
        return userList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public User toEntity(UserRequest userRequest) {
        return mapper.map(userRequest, User.class);
    }

    public void copyPropertiesToEntity(UserRequest userRequest, User user) {
        mapper.map(userRequest, user);
    }
}
