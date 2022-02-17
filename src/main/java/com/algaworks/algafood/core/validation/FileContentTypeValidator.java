package com.algaworks.algafood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = List.of(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return file == null || this.allowedContentTypes.contains(file.getContentType());
    }
}
