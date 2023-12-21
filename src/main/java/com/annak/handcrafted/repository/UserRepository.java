package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
