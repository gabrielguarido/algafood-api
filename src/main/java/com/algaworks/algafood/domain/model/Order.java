package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.model.enumerator.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "[order]")
public class Order {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;

    @Embedded
    @JsonIgnore
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @CreationTimestamp
    private OffsetDateTime created;

    private OffsetDateTime confirmed;
    private OffsetDateTime cancelled;
    private OffsetDateTime delivered;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    public void calculateTotalValue() {
        this.subtotal = getItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void defineDeliveryFee() {
        setDeliveryFee(getRestaurant().getDeliveryFee());
    }

    public void linkOrderToItems() {
        getItems().forEach(item -> item.setOrder(this));
    }
}
