package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.Product;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByDeletedIsFalse();

    List<Product> findAllByCategoryIdAndDeletedIsFalse(Long categoryId);

    List<Product> findAllByPriceBetweenAndDeletedIsFalse(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByPriceAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByPriceDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByCreationDateAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByCreationDateDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByPriceAscCreationDateAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByPriceAscCreationDateDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByPriceDescCreationDateAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenAndDeletedIsFalseOrderByPriceDescCreationDateDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    Optional<Product> findByIdAndDeletedIsFalse(Long id);

    @Query("SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.colors col " +
            "WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(p.keyWords) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(col.name) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "   AND p.deleted = false")
    List<Product> searchProductsBySearchLineAndDeletedIsFalse(@Param("keyword") String searchLine);

    @Query("SELECT DISTINCT pio.product " +
            "FROM ProductInOrder pio " +
            "WHERE NOT (pio.order.user.id = :userId) " +
            "   AND NOT (pio.product.id = :productId)" +
            "   AND pio.order IN (" +
            "       SELECT o " +
            "       FROM Order o " +
            "       JOIN ProductInOrder pio2 " +
            "       ON o = pio2.order " +
            "       WHERE pio2.product.id = :productId ) " +
            "   AND NOT pio.product.deleted")
    List<Product> getRecommendedByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId, Limit limit);
}
