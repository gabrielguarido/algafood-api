package com.algaworks.algafood.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal deliveryFee;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Embedded
    private Address address;

    private Boolean active = Boolean.TRUE;

    private Boolean open = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updated;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public void activate() {
        setActive(true);
    }

    public void deactivate() {
        setActive(false);
    }

    public void open() {
        setOpen(true);
    }

    public void close() {
        setOpen(false);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        getPaymentMethods().add(paymentMethod);
    }

    public void removePaymentMethod(PaymentMethod paymentMethod) {
        getPaymentMethods().remove(paymentMethod);
    }
}
