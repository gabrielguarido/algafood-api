package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.request.CityRequest;
import com.algaworks.algafood.api.model.response.CityResponse;
import com.algaworks.algafood.domain.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "Cities")
@RequestMapping(value = "city", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @ApiOperation("Lists all the cities that are available to use")
    public ResponseEntity<List<CityResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(cityService.list());
    }

    @GetMapping("/{id}")
    @ApiOperation("Finds a city by its ID value")
    public ResponseEntity<CityResponse> find(@ApiParam(value = "City identifier", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(cityService.find(id));
    }

    @PostMapping
    @ApiOperation("Registers a new city")
    public ResponseEntity<CityResponse> create(@ApiParam(value = "Request body with data about the new city") @RequestBody @Valid CityRequest cityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(cityRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation("Updates an existing city with new values")
    public ResponseEntity<CityResponse> update(@ApiParam(value = "City identifier", example = "1") @PathVariable Long id,
                                               @ApiParam(value = "Request body with updated data about the existing city") @RequestBody @Valid CityRequest cityRequest) {
        return ResponseEntity.ok(cityService.update(id, cityRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletes an existing city by its ID value")
    public ResponseEntity<Void> delete(@ApiParam(value = "City identifier", example = "1") @PathVariable Long id) {
        cityService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
