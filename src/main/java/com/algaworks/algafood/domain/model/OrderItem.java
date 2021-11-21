package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@Entity
public class OrderItem {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer amount;
    private String observation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    public void calculateTotalPrice() {
        var unitPrice = this.getUnitPrice() == null ? BigDecimal.ZERO : this.getUnitPrice();
        var amount = this.getAmount() == null ? 0 : this.getAmount();

        this.setTotalPrice(unitPrice.multiply(new BigDecimal(amount)));
    }
}
