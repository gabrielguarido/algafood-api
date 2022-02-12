package com.algaworks.algafood.domain.repository.query;

import com.algaworks.algafood.domain.model.ProductPicture;

public interface ProductQueryRepository {

    ProductPicture save(ProductPicture picture);
}
