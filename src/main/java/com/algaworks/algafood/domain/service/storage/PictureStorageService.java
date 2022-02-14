package com.algaworks.algafood.domain.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PictureStorageService {

    void store(Picture picture);

    void remove(String fileName);

    InputStream retrieve(String fileName);

    default String generateFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    @Getter
    @Builder
    class Picture {

        private String fileName;
        private InputStream inputStream;
    }
}
