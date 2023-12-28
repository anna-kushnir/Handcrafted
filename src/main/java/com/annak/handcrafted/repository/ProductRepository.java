package com.annak.handcrafted.repository;

import com.annak.handcrafted.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<Product> findAllByPriceBetweenOrderByPriceAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenOrderByPriceDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<Product> findAllByPriceBetweenOrderByCreationDateAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenOrderByCreationDateDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<Product> findAllByPriceBetweenOrderByPriceAscCreationDateAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenOrderByPriceAscCreationDateDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenOrderByPriceDescCreationDateAsc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
    List<Product> findAllByPriceBetweenOrderByPriceDescCreationDateDesc(BigDecimal priceLimitFrom, BigDecimal priceLimitTo);
}
