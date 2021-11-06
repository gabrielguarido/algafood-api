package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.PaymentMethodRequest;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PaymentMethodTransformer {

    private final ModelMapper mapper;

    @Autowired
    public PaymentMethodTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PaymentMethodResponse toResponse(PaymentMethod paymentMethod) {
        return mapper.map(paymentMethod, PaymentMethodResponse.class);
    }

    public List<PaymentMethodResponse> toResponse(Collection<PaymentMethod> paymentMethodList) {
        return paymentMethodList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public PaymentMethod toEntity(PaymentMethodRequest paymentMethodRequest) {
        return mapper.map(paymentMethodRequest, PaymentMethod.class);
    }
}
