package com.algaworks.algafood.api.model.request;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Data
public class ProductPictureRequest {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
    MultipartFile file;
}
