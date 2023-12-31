package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {
    List<ProductInOrder> findAllByOrderId(Long orderId);
}
