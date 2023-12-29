package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT", schema = "HANDCRAFTED_SCHEMA")
@Getter
@Setter
@EqualsAndHashCode(of = {"name", "creationDate"})
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_PRODUCT", sequenceName = "HANDCRAFTED_SCHEMA.PRODUCT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_PRODUCT")
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "KEY_WORDS")
    private String keyWords;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "WITH_DISCOUNT")
    private boolean withDiscount;

    @Column(name = "DISCOUNTED_PRICE")
    private BigDecimal discountedPrice;

    @Column(name = "IN_STOCK")
    private boolean inStock;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToMany
    @JoinTable(
            schema = "HANDCRAFTED_SCHEMA",
            name = "COLOR_IN_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COLOR_ID")
    )
    private List<Color> colors = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();
}
