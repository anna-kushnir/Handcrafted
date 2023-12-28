package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PHOTO", schema = "HANDCRAFTED_SCHEMA")
@Getter
@Setter
@EqualsAndHashCode(of = {"name", "data"})
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_PHOTO", sequenceName = "HANDCRAFTED_SCHEMA.PHOTO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_PHOTO")
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Lob
    @Column(name = "DATA")
    private byte[] data;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
