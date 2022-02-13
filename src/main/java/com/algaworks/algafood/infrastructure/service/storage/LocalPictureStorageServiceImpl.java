package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.storage.PictureStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPictureStorageServiceImpl implements PictureStorageService {

    private static final String STORE_EXCEPTION_MESSAGE = "It was not possible to store the file: %s";
    private static final String REMOVE_EXCEPTION_MESSAGE = "It was not possible to remove the file: %s";

    @Value("${local.storage.path}")
    private String storagePath;

    @Override
    public void store(Picture picture) {
        try {
            var filePath = Path.of(storagePath, picture.getFileName());

            FileCopyUtils.copy(picture.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException(String.format(STORE_EXCEPTION_MESSAGE, picture.getFileName()), e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            var filePath = Path.of(storagePath, fileName);

            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException(String.format(STORE_EXCEPTION_MESSAGE, fileName), e);
        }
    }
}
