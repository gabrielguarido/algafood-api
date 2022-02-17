package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.storage.PictureStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

@NoArgsConstructor
public class S3PictureStorageServiceImpl implements PictureStorageService {

    private static final String STORE_EXCEPTION_MESSAGE = "It was not possible to store the file %s in the AWS S3 storage";
    private static final String REMOVE_EXCEPTION_MESSAGE = "It was not possible to remove the file %s  from AWS S3";
    private static final String RETRIEVE_EXCEPTION_MESSAGE = "It was not possible to retrieve the file %s  from AWS S3";

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void store(Picture picture) {
        try {
            var bucket = storageProperties.getS3().getBucket();
            var key = getFilePath(picture.getFileName());
            var inputStream = picture.getInputStream();
            var objectMetadata = new ObjectMetadata();

            objectMetadata.setContentType("");

            var request = new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(request);
        } catch (Exception e) {
            throw new StorageException(String.format(STORE_EXCEPTION_MESSAGE, picture.getFileName()), e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            var bucket = storageProperties.getS3().getBucket();
            var filePath = getFilePath(fileName);

            var request = new DeleteObjectRequest(bucket, filePath);

            amazonS3.deleteObject(request);
        } catch (Exception e) {
            throw new StorageException(String.format(REMOVE_EXCEPTION_MESSAGE, fileName), e);
        }
    }

    @Override
    public PictureResponse retrieve(String fileName) {
        try {
            var bucket = storageProperties.getS3().getBucket();
            var filePath = getFilePath(fileName);

            URL url = amazonS3.getUrl(bucket, filePath);

            return PictureResponse.builder()
                    .url(url.toString())
                    .build();
        } catch (Exception e) {
            throw new StorageException(String.format(RETRIEVE_EXCEPTION_MESSAGE, fileName), e);
        }
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getPath(), fileName);
    }
}
