package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.CityControllerDocumentation;
import com.algaworks.algafood.api.controller.security.CityControllerSecurity;
import com.algaworks.algafood.api.model.request.CityRequest;
import com.algaworks.algafood.api.model.response.CityResponse;
import com.algaworks.algafood.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
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

import static com.algaworks.algafood.api.controller.util.ResourceUriUtil.composeUri;

@RestController
@RequestMapping(value = "city", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerDocumentation, CityControllerSecurity {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(cityService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(cityService.find(id));
    }

    @PostMapping
    public ResponseEntity<CityResponse> create(@RequestBody @Valid CityRequest cityRequest) {
        var newCity = cityService.save(cityRequest);

        return ResponseEntity.created(composeUri(newCity.getId())).body(newCity);
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
