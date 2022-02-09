package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class OrderSpecs {

    private OrderSpecs() {
    }

    public static Specification<Order> withFilter(OrderFilter filter) {
        return (root, query, builder) -> {
            if (Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("category");
                root.fetch("client");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreatedStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("created"), filter.getCreatedStart()));
            }

            if (filter.getCreatedEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("created"), filter.getCreatedEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
