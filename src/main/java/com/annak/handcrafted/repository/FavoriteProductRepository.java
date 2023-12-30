package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.FavoriteProduct;
import com.annak.handcrafted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {
    List<FavoriteProduct> findAllByUser(User user);

    boolean existsByUserUserNameAndProductId(String userUserName, Long productId);

    void deleteByUserUserNameAndProductId(String userUserName, Long productId);

    void deleteAllByProductId(Long productId);
}
