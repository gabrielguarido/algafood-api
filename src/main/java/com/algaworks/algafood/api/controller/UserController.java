package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.request.PasswordRequest;
import com.algaworks.algafood.api.model.request.UserRequest;
import com.algaworks.algafood.api.model.request.UserWithPasswordRequest;
import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(userService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(userService.find(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserWithPasswordRequest userWithPasswordRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userWithPasswordRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.update(id, userRequest));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid PasswordRequest passwordRequest) {
        userService.updatePassword(id, passwordRequest);

        return ResponseEntity.noContent().build();
    }
}
