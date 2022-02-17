package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.enumerator.StorageType;
import com.algaworks.algafood.domain.service.storage.PictureStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalPictureStorageServiceImpl;
import com.algaworks.algafood.infrastructure.service.storage.S3PictureStorageServiceImpl;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Autowired
    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var accessKey = storageProperties.getS3().getIdAccessKey();
        var secretKey = storageProperties.getS3().getSecretAccessKey();
        var region = storageProperties.getS3().getRegion();

        var credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));

        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentials)
                .withRegion(region)
                .build();
    }

    @Bean
    public PictureStorageService pictureStorageService() {
        if (storageProperties.getType().equals(StorageType.LOCAL)) {
            return new LocalPictureStorageServiceImpl();
        } else {
            return new S3PictureStorageServiceImpl();
        }
    }
}
