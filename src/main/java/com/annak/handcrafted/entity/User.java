package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "USER")
@Getter
@Setter
@EqualsAndHashCode(of = {"userName"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER", schema = "HANDCRAFTED_SCHEMA")
public class User {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_USER", sequenceName = "HANDCRAFTED_SCHEMA.USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_USER")
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_PHONE")
    private Long userPhone;

    @ManyToMany
    @JoinTable(
            name = "FAVORITE_PRODUCTS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<Product> favoriteProducts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "PRODUCTS_IN_CART",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<Product> productsInCart = new ArrayList<>();


    public User(Long id, String userName, String name, String surname, String password) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
}
