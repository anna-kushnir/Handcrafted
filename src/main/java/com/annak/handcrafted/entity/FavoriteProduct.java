package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FAVORITE_PRODUCT", schema = "HANDCRAFTED_SCHEMA")
@Getter
@Setter
@EqualsAndHashCode(of = {"user", "product"})
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProduct {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_FAVORITE_PRODUCT", sequenceName = "HANDCRAFTED_SCHEMA.FAVORITE_PRODUCT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_FAVORITE_PRODUCT")
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
