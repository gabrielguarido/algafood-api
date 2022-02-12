package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.ProductPicture;
import com.algaworks.algafood.domain.repository.query.ProductQueryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductRepositoryImpl implements ProductQueryRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public ProductPicture save(ProductPicture picture) {
        return manager.merge(picture);
    }
}
