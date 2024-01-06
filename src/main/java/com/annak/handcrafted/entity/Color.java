package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COLOR", schema = "HANDCRAFTED_SCHEMA")
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_COLOR", sequenceName = "HANDCRAFTED_SCHEMA.COLOR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_COLOR")
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;
}
