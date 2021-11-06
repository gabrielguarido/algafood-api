package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.PaymentMethodRequest;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.api.transformer.PaymentMethodTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.PaymentMethodNotFoundException;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentMethodService {

    private static final String PAYMENT_METHOD_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The payment method %s already exists with ID %s";

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodTransformer paymentMethodTransformer;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, PaymentMethodTransformer paymentMethodTransformer) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodTransformer = paymentMethodTransformer;
    }

    public List<PaymentMethodResponse> list() {
        return paymentMethodTransformer.toResponse(paymentMethodRepository.findAll());
    }

    public PaymentMethodResponse find(Long id) {
        return paymentMethodTransformer.toResponse(verifyIfExists(id));
    }

    @Transactional
    public PaymentMethodResponse save(PaymentMethodRequest paymentMethodRequest) {
        var paymentMethod = paymentMethodTransformer.toEntity(paymentMethodRequest);
        paymentMethod.setMethod(paymentMethod.getMethod().toUpperCase());

        validateMethod(paymentMethod.getMethod());

        return paymentMethodTransformer.toResponse(paymentMethodRepository.save(paymentMethod));
    }

    @Transactional
    public void delete(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
            paymentMethodRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(id);
        }
    }

    public PaymentMethod verifyIfExists(Long id) {
        return paymentMethodRepository.findById(id).orElseThrow(() -> new PaymentMethodNotFoundException(id));
    }

    private void validateMethod(String method) {
        var paymentMethod = paymentMethodRepository.findByMethod(method);

        if (paymentMethod.isPresent()) {
            throw new BusinessException(String.format(PAYMENT_METHOD_ALREADY_EXISTS_EXCEPTION_MESSAGE, method, paymentMethod.get().getId()));
        }
    }
}
