package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "user/{userId}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileController {

    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> list(@PathVariable Long userId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(userService.listProfiles(userId));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Void> addProfile(@PathVariable Long userId, @PathVariable Long profileId) {
        userService.addProfile(userId, profileId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> removeProfile(@PathVariable Long userId, @PathVariable Long profileId) {
        userService.removeProfile(userId, profileId);

        return ResponseEntity.noContent().build();
    }
}
