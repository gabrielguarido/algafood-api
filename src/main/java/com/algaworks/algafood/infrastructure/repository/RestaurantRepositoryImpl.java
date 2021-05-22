package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.repository.query.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreeShipping;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withNameLike;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Lazy
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialShippingTax, BigDecimal finalShippingTax) {
        var builder = manager.getCriteriaBuilder();
        var criteria = builder.createQuery(Restaurant.class);
        var root = criteria.from(Restaurant.class);
        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (initialShippingTax != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("shippingTax"), initialShippingTax));
        }

        if (finalShippingTax != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("shippingTax"), finalShippingTax));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeShippingTax(String name) {
        return restaurantRepository.findAll(withFreeShipping().and(withNameLike(name)));
    }
}
