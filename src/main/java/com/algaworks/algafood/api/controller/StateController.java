package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping(value = "state", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public ResponseEntity<List<State>> list() {
        return ResponseEntity.ok(stateService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> find(@PathVariable Long id) {
        return ResponseEntity.ok(stateService.find(id));
    }

    @PostMapping
    public ResponseEntity<State> create(@RequestBody State state) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stateService.save(state));
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state) {
        return ResponseEntity.ok(stateService.update(id, state));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id) {
        stateService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
