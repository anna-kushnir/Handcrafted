package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "CATEGORY")
@EqualsAndHashCode(of = {"name"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CATEGORY", schema = "HANDCRAFTED_SCHEMA")
public class Category {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_CATEGORY", sequenceName = "HANDCRAFTED_SCHEMA.CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_CATEGORY")
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
