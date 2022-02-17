package com.algaworks.algafood.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductPicture {

    @Id
    @EqualsAndHashCode.Include
    private Long productId;

    @MapsId
    @OneToOne
    private Product product;

    private String fileName;

    private String contentType;

    private Long fileSize;
}
