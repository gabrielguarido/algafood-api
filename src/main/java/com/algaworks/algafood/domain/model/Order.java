package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.model.enumerator.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
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

import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.CANCELED;
import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.CONFIRMED;
import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.CREATED;
import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.DELIVERED;

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
    private OrderStatus status = CREATED;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public void calculateTotalValue() {
        getItems().forEach(OrderItem::calculateTotalPrice);

        this.subtotal = getItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalPrice = this.subtotal.add(this.deliveryFee);
    }

    public void defineDeliveryFee() {
        setDeliveryFee(getRestaurant().getDeliveryFee());
    }

    public void linkOrderToItems() {
        getItems().forEach(item -> item.setOrder(this));
    }

    public void confirm() {
        setStatus(CONFIRMED);
        setConfirmed(OffsetDateTime.now());
    }

    public void deliver() {
        setStatus(DELIVERED);
        setDelivered(OffsetDateTime.now());
    }

    public void cancel() {
        setStatus(CANCELED);
        setCancelled(OffsetDateTime.now());
    }

    private void setStatus(OrderStatus targetStatus) {
        if (!getStatus().canUpdateTo(targetStatus)) {
            throw new BusinessException(
                    String.format("Failed to update the order status from %s to %s", getStatus(), targetStatus)
            );
        }
        this.status = targetStatus;
    }
}
