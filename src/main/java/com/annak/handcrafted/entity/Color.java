package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "COLOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "COLOR", schema = "HANDCRAFTED_SCHEMA")
public class Color {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_COLOR", sequenceName = "HANDCRAFTED_SCHEMA.COLOR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_COLOR")
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "colors", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Product> productsWithColor = new ArrayList<>();
}
