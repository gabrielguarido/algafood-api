package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.storage.PictureStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@NoArgsConstructor
public class LocalPictureStorageServiceImpl implements PictureStorageService {

    private static final String STORE_EXCEPTION_MESSAGE = "It was not possible to store the file %s in the local storage";
    private static final String REMOVE_EXCEPTION_MESSAGE = "It was not possible to remove the file %s  from local storage";
    private static final String RETRIEVE_EXCEPTION_MESSAGE = "It was not possible to retrieve the file %s  from local storage";

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void store(Picture picture) {
        try {
            var filePath = Path.of(storageProperties.getLocal().getPath(), picture.getFileName());

            FileCopyUtils.copy(picture.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException(String.format(STORE_EXCEPTION_MESSAGE, picture.getFileName()), e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            var filePath = Path.of(storageProperties.getLocal().getPath(), fileName);

            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException(String.format(REMOVE_EXCEPTION_MESSAGE, fileName), e);
        }
    }

    @Override
    public PictureResponse retrieve(String fileName) {
        try {
            var filePath = Path.of(storageProperties.getLocal().getPath(), fileName);

            return PictureResponse.builder()
                    .inputStream(Files.newInputStream(filePath))
                    .build();
        } catch (Exception e) {
            throw new StorageException(String.format(RETRIEVE_EXCEPTION_MESSAGE, fileName), e);
        }
    }
}
