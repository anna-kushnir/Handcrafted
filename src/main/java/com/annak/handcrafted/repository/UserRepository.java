package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByIdAsc();

    User findByUserName(String username);

    boolean existsByUserName(String username);
}
