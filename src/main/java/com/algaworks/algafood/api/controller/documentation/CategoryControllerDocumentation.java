package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.CategoryRequest;
import com.algaworks.algafood.api.model.response.CategoryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Api(tags = "Categories")
public interface CategoryControllerDocumentation {

    @ApiOperation("Returns a page with all the food categories that are available to use")
    ResponseEntity<Page<CategoryResponse>> list(Pageable pageable);

    @ApiOperation("Finds a category by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Category not found", response = Error.class)
    })
    ResponseEntity<CategoryResponse> find(@ApiParam(value = "Category identifier", example = "1") Long id);

    @ApiOperation("Finds a category by its type")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid type"),
            @ApiResponse(code = 404, message = "Category type not found", response = Error.class)
    })
    ResponseEntity<CategoryResponse> findByType(@ApiParam(value = "Category type", example = "Burger") String type);

    @ApiOperation("Verifies if a category exists by its type")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid type")
    })
    ResponseEntity<Boolean> existsByType(@ApiParam(value = "Category type", example = "Burger") String type);

    @ApiOperation("Returns the first category found in the search query")
    ResponseEntity<CategoryResponse> findFirst();

    @ApiOperation("Registers a new food category")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Category registered successfully")
    })
    ResponseEntity<CategoryResponse> create(@ApiParam(value = "Request body with data about the new category") CategoryRequest categoryRequest);

    @ApiOperation("Updates an existing category with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Category updated successfully"),
            @ApiResponse(code = 404, message = "Category not found", response = Error.class)
    })
    ResponseEntity<CategoryResponse> update(@ApiParam(value = "Category identifier", example = "1") Long id,
                                            @ApiParam(value = "Request body with updated data about the existing category") CategoryRequest categoryRequest);

    @ApiOperation("Deletes an existing category by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Category removed successfully"),
            @ApiResponse(code = 404, message = "Category not found", response = Error.class)
    })
    ResponseEntity<Void> delete(@ApiParam(value = "Category identifier", example = "1") Long id);
}
