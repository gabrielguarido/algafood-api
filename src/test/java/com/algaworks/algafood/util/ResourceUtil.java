package com.algaworks.algafood.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ResourceUtil {

    public static String getContentFromResource(String resourceName) {
        try {
            InputStream stream = ResourceUtil.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
