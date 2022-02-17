package com.algaworks.algafood.domain.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PictureStorageService {

    void store(Picture picture);

    void remove(String fileName);

    PictureResponse retrieve(String fileName);

    default String generateFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    @Getter
    @Builder
    class Picture {

        private String fileName;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class PictureResponse {

        private String url;
        private InputStream inputStream;
    }
}
