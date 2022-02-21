package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.StateControllerDocumentation;
import com.algaworks.algafood.api.model.request.StateRequest;
import com.algaworks.algafood.api.model.response.StateResponse;
import com.algaworks.algafood.domain.service.StateService;
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
@RequestMapping(value = "state", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerDocumentation {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(stateService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(stateService.find(id));
    }

    @PostMapping
    public ResponseEntity<StateResponse> create(@RequestBody @Valid StateRequest stateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stateService.save(stateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateResponse> update(@PathVariable Long id, @RequestBody @Valid StateRequest stateRequest) {
        return ResponseEntity.ok(stateService.update(id, stateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stateService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
