package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.entity.embedded.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByFormationDate(User user);

    List<Order> findAllByStatusOrderByFormationDate(Status status);

    List<Order> findAllByUserPhoneOrderByFormationDate(Long userPhone);
}
