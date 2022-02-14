package com.algaworks.algafood.api.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;

import java.io.InputStream;

@Data
@Builder
public class ProductPictureResponse {

    private InputStream inputStream;
    private MediaType mediaType;
}
