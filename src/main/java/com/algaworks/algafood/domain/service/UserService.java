package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.PasswordRequest;
import com.algaworks.algafood.api.model.request.UserRequest;
import com.algaworks.algafood.api.model.request.UserWithPasswordRequest;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.api.transformer.ProfileTransformer;
import com.algaworks.algafood.api.transformer.UserTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.UserNotFoundException;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String EMAIL_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The given email %s is already taken, please choose another one";
    private static final String INVALID_PASSWORD_EXCEPTION_MESSAGE = "The given current password is invalid";
    private static final String PASSWORD_MISMATCH_EXCEPTION_MESSAGE = "The given new passwords do not match";

    private final UserRepository userRepository;

    private final UserTransformer userTransformer;

    private final ProfileService profileService;

    private final ProfileTransformer profileTransformer;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserTransformer userTransformer, ProfileService profileService,
                       ProfileTransformer profileTransformer, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
        this.profileService = profileService;
        this.profileTransformer = profileTransformer;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> list() {
        return userTransformer.toResponse(userRepository.findAll());
    }

    public UserResponse find(Long id) {
        return userTransformer.toResponse(verifyIfExists(id));
    }

    @Transactional
    public UserResponse save(UserWithPasswordRequest userWithPasswordRequest) {
        var user = userTransformer.toEntity(userWithPasswordRequest);

        validateEmail(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userTransformer.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        try {
            var existingUser = verifyIfExists(id);

            validateEmailForUpdate(id, userRequest.getEmail());

            userTransformer.copyPropertiesToEntity(userRequest, existingUser);

            return userTransformer.toResponse(userRepository.save(existingUser));
        } catch (UserNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void updatePassword(Long id, PasswordRequest passwordRequest) {
        var existingUser = verifyIfExists(id);

        validatePassword(existingUser, passwordRequest);

        existingUser.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
    }

    public List<ProfileResponse> listProfiles(Long userId) {
        var user = verifyIfExists(userId);

        return profileTransformer.toResponse(user.getProfiles());
    }

    @Transactional
    public void addProfile(Long userId, Long profileId) {
        var user = verifyIfExists(userId);

        var profile = profileService.verifyIfExists(profileId);

        user.addProfile(profile);
    }

    @Transactional
    public void removeProfile(Long userId, Long profileId) {
        var user = verifyIfExists(userId);

        var profile = profileService.verifyIfExists(profileId);

        user.removeProfile(profile);
    }

    public User verifyIfExists(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(String.format(EMAIL_ALREADY_EXISTS_EXCEPTION_MESSAGE, email));
        }
    }

    private void validateEmailForUpdate(Long id, String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (!user.get().getId().equals(id)) {
                throw new BusinessException(String.format(EMAIL_ALREADY_EXISTS_EXCEPTION_MESSAGE, email));
            }
        }
    }

    private void validatePassword(User existingUser, PasswordRequest passwordRequest) {
        validateCurrentPassword(existingUser, passwordRequest);
        validateNewPassword(passwordRequest);
    }

    private void validateCurrentPassword(User existingUser, PasswordRequest passwordRequest) {
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), existingUser.getPassword())) {
            throw new BusinessException(INVALID_PASSWORD_EXCEPTION_MESSAGE);
        }
    }

    private void validateNewPassword(PasswordRequest passwordRequest) {
        if (!passwordRequest.getNewPassword().equals(passwordRequest.getRepeatPassword())) {
            throw new BusinessException(PASSWORD_MISMATCH_EXCEPTION_MESSAGE);
        }
    }
}
