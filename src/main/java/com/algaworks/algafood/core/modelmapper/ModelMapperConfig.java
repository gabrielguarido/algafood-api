package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.request.OrderItemRequest;
import com.algaworks.algafood.api.model.response.AddressResponse;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Address.class, AddressResponse.class)
                .addMapping(src -> src.getCity().getState().getName(),
                        (dest, value) -> dest.getCity().setState((String) value));

        modelMapper.createTypeMap(OrderItemRequest.class, OrderItem.class)
                .addMappings(mapper -> mapper.skip(OrderItem::setId));

        return modelMapper;
    }
}
