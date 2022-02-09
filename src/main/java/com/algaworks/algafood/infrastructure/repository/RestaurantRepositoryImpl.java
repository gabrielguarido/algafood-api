package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.repository.query.RestaurantQueryRepository;
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

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreeDelivery;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withNameLike;

@Repository
public class RestaurantRepositoryImpl implements RestaurantQueryRepository {

    @PersistenceContext
    private EntityManager manager;

    @Lazy
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        var builder = manager.getCriteriaBuilder();
        var criteria = builder.createQuery(Restaurant.class);
        var root = criteria.from(Restaurant.class);
        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (initialDeliveryFee != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryFee"), initialDeliveryFee));
        }

        if (finalDeliveryFee != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("deliveryFee"), finalDeliveryFee));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeDeliveryFee(String name) {
        return restaurantRepository.findAll(withFreeDelivery().and(withNameLike(name)));
    }
}
