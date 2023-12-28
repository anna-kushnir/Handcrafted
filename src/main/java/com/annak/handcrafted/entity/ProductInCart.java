package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_IN_CART", schema = "HANDCRAFTED_SCHEMA")
@Getter
@Setter
@EqualsAndHashCode(of = {"user", "product"})
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCart {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_PRODUCT_IN_CART", sequenceName = "HANDCRAFTED_SCHEMA.PRODUCT_IN_CART_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_PRODUCT_IN_CART")
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "QUANTITY")
    private Long quantityInCart;
}
