package com.algaworks.algafood.api.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;

import java.io.InputStream;

@Data
@Builder
public class ProductPictureResponse {

    private InputStream inputStream;
    private String url;
    private MediaType mediaType;

    public boolean hasUrl() {
        return this.url != null;
    }

    public boolean hasInputStream() {
        return this.inputStream != null;
    }
}
