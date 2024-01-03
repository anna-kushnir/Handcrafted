package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {
    List<ProductInCart> findAllByUser(User user);

    Optional<ProductInCart> findByUserAndProductId(User user, Long productId);

    boolean existsByUserUserNameAndProductId(String userUserName, Long productId);

    void deleteByUserUserNameAndProductId(String userUserName, Long productId);

    void deleteAllByProductId(Long productId);
}
