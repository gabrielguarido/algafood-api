package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPicture;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.query.ProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {

    @Query("from Product where restaurant.id = :restaurantId and id = :productId")
    Optional<Product> findById(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

    List<Product> findAllByRestaurant(Restaurant restaurant);

    @Query("from Product where active = true and restaurant = :restaurant")
    List<Product> findAllActiveByRestaurant(Restaurant restaurant);

    @Query("select pp from ProductPicture pp join pp.product p where p.restaurant.id = :restaurantId and pp.product.id = :productId")
    Optional<ProductPicture> findPictureById(Long restaurantId, Long productId);
}
