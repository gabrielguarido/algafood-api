package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.request.OrderItemRequest;
import com.algaworks.algafood.api.model.response.AddressResponse;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.OrderItem;
import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

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

        Provider<UUID> uuidProvider = new AbstractProvider<>() {
            @Override
            public UUID get() {
                return UUID.randomUUID();
            }
        };

        final Converter<String, UUID> uuidConverter = new AbstractConverter<>() {
            @Override
            protected UUID convert(final String source) {
                return UUID.fromString(source);
            }
        };

        modelMapper.createTypeMap(String.class, UUID.class);
        modelMapper.addConverter(uuidConverter);
        modelMapper.getTypeMap(String.class, UUID.class).setProvider(uuidProvider);

        return modelMapper;
    }
}
