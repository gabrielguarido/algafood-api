package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.ProfileRequest;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.domain.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProfileTransformer {

    private final ModelMapper mapper;

    @Autowired
    public ProfileTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProfileResponse toResponse(Profile profile) {
        return mapper.map(profile, ProfileResponse.class);
    }

    public List<ProfileResponse> toResponse(Collection<Profile> profileList) {
        return profileList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public Profile toEntity(ProfileRequest profileRequest) {
        return mapper.map(profileRequest, Profile.class);
    }

    public void copyPropertiesToEntity(ProfileRequest profileRequest, Profile profile) {
        mapper.map(profileRequest, profile);
    }
}
