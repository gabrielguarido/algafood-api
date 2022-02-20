package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.request.PaymentMethodRequest;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.domain.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "payment-method", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentMethodService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentMethodService.find(id));
    }

    @PostMapping
    public ResponseEntity<PaymentMethodResponse> create(@RequestBody @Valid PaymentMethodRequest paymentMethodRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodService.save(paymentMethodRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentMethodService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
