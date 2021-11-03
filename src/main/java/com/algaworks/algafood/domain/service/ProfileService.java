package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.ProfileRequest;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.api.transformer.ProfileTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ProfileNotFoundException;
import com.algaworks.algafood.domain.model.Profile;
import com.algaworks.algafood.domain.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    private static final String PROFILE_IN_USE_EXCEPTION_MESSAGE = "The profile ID %s is currently being used and cannot be removed";
    private static final String PROFILE_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The profile name %s already exists with ID %s";

    private final ProfileRepository profileRepository;

    private final ProfileTransformer profileTransformer;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, ProfileTransformer profileTransformer) {
        this.profileRepository = profileRepository;
        this.profileTransformer = profileTransformer;
    }

    public List<ProfileResponse> list() {
        return profileTransformer.toResponse(profileRepository.findAll());
    }

    public ProfileResponse find(Long id) {
        return profileTransformer.toResponse(verifyIfExists(id));
    }

    @Transactional
    public ProfileResponse save(ProfileRequest profileRequest) {
        var profile = profileTransformer.toEntity(profileRequest);

        validateName(profile.getName());

        return profileTransformer.toResponse(profileRepository.save(profile));
    }

    @Transactional
    public ProfileResponse update(Long id, ProfileRequest profileRequest) {
        var existingProfile = verifyIfExists(id);

        validateName(profileRequest.getName());

        profileTransformer.copyPropertiesToEntity(profileRequest, existingProfile);

        try {
            return profileTransformer.toResponse(profileRepository.save(existingProfile));
        } catch (ProfileNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            profileRepository.deleteById(id);
            profileRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProfileNotFoundException(id);
        }
    }

    public Profile verifyIfExists(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));
    }

    private void validateName(String name) {
        var profile = profileRepository.findByName(name);

        if (profile.isPresent()) {
            throw new BusinessException(String.format(PROFILE_ALREADY_EXISTS_EXCEPTION_MESSAGE, name, profile.get().getId()));
        }
    }
}
