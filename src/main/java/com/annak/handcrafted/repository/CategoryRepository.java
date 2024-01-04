package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CategoryDto> findAllByOrderByNameAsc();

    @Query("SELECT c.name AS categoryName, COUNT(pio.id) AS countInOrders " +
            "FROM ProductInOrder pio JOIN pio.product p JOIN p.category c GROUP BY c.name ORDER BY countInOrders DESC")
    List<Object[]> getCategoryStatistics();
}
