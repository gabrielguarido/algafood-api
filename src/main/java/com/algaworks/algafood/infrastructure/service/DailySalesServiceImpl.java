package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.model.enumerator.OrderStatus;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;
import com.algaworks.algafood.domain.repository.query.DailySalesServiceQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DailySalesServiceImpl implements DailySalesServiceQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySales> findDailySales(DailySalesFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var criteria = builder.createQuery(DailySales.class);
        var root = criteria.from(Order.class);
        var predicates = new ArrayList<Predicate>();

        var dateFunctionCreated = builder.function("date", Date.class, root.get("created"));

        var selection = builder.construct(DailySales.class,
                dateFunctionCreated,
                builder.count(root.get("id")),
                builder.sum(root.get("totalPrice")));

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getCreatedStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("created"), filter.getCreatedStart()));
        }

        if (filter.getCreatedEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("created"), filter.getCreatedEnd()));
        }

        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        criteria.select(selection);
        criteria.where(predicates.toArray(new Predicate[0]));
        criteria.groupBy(dateFunctionCreated);

        return manager.createQuery(criteria).getResultList();
    }
}
