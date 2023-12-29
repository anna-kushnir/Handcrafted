package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COLOR_IN_PRODUCT", schema = "HANDCRAFTED_SCHEMA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColorInProduct {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_COLOR_IN_PRODUCT", sequenceName = "HANDCRAFTED_SCHEMA.COLOR_IN_PRODUCT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_COLOR_IN_PRODUCT")
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COLOR_ID")
    private Color color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
