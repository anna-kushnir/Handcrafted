package com.annak.handcrafted.repository;

import com.annak.handcrafted.dto.ColorDto;
import com.annak.handcrafted.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {
    List<ColorDto> findAllByOrderByNameAsc();

    Color findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT c.name AS colorName, COUNT(pio.id) AS countInOrders " +
            "FROM ProductInOrder pio JOIN pio.product p JOIN p.colors c GROUP BY c.name ORDER BY countInOrders DESC")
    List<Object[]> getColorStatistics();
}
