package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

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

    boolean existsByIdAndDeletedIsFalse(Long id);
}
