package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {
    List<ProductInCart> findAllByUser(User user);

    boolean existsByUserUserNameAndProductId(String userUserName, Long productId);

    void deleteByUserUserNameAndProductId(String userUserName, Long productId);

    void deleteAllByProductId(Long productId);
}
