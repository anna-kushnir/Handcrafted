package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
