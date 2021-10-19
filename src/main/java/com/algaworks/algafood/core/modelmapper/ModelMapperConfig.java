package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.AddressResponse;
import com.algaworks.algafood.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var addressTypeMap = modelMapper.createTypeMap(Address.class, AddressResponse.class);
        addressTypeMap.<String>addMapping(
                src -> src.getCity().getState().getName(),
                (dest, value) -> dest.getCity().setState(value));

        return modelMapper;
    }
}
