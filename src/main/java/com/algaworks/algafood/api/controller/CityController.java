package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.request.CityRequest;
import com.algaworks.algafood.api.model.response.CityResponse;
import com.algaworks.algafood.domain.service.CityService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "city", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> list() {
        return ResponseEntity.ok(cityService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.find(id));
    }

    @PostMapping
    public ResponseEntity<CityResponse> create(@RequestBody @Valid CityRequest cityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(cityRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> update(@PathVariable Long id, @RequestBody @Valid CityRequest cityRequest) {
        return ResponseEntity.ok(cityService.update(id, cityRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
