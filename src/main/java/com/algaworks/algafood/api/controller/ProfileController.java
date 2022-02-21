package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.ProfileControllerDocumentation;
import com.algaworks.algafood.api.model.request.ProfileRequest;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.domain.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController implements ProfileControllerDocumentation {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(profileService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(profileService.find(id));
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> create(@RequestBody @Valid ProfileRequest profileRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.save(profileRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> update(@PathVariable Long id, @RequestBody @Valid ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.update(id, profileRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        profileService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
