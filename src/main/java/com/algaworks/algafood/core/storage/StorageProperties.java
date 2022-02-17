package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.enumerator.StorageType;
import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();

    private StorageType type = StorageType.LOCAL;

    @Getter
    @Setter
    public static class Local {

        private String path;
    }

    @Getter
    @Setter
    public static class S3 {

        private String idAccessKey;
        private String secretAccessKey;
        private String bucket;
        private Regions region;
        private String path;
    }
}
