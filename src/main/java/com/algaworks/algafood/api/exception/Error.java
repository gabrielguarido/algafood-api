package com.algaworks.algafood.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class Error {

    @ApiModelProperty(example = "400")
    private final Integer status;

    @ApiModelProperty(example = "2022-02-21T00:03:09.5106695Z")
    private final OffsetDateTime timestamp;

    @ApiModelProperty(example = "Invalid payload")
    private final String title;

    @ApiModelProperty(example = "One or more fields are invalid. Fill in correctly and try again")
    private final String detail;

    @ApiModelProperty("List of fields that generated the error")
    private final List<Field> fields;

    @Getter
    @Builder
    @ApiModel(description = "Error field")
    public static class Field {

        @ApiModelProperty(example = "name")
        private final String name;

        @ApiModelProperty(example = "must not be blank")
        private final String detail;
    }
}
