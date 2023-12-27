package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "USER")
@EqualsAndHashCode(of = {"userName"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER", schema = "HANDCRAFTED_SCHEMA")
public class User implements UserDetails {
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

    @Column(name = "ACTIVE")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            schema = "HANDCRAFTED_SCHEMA",
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            schema = "HANDCRAFTED_SCHEMA",
            name = "FAVORITE_PRODUCTS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<Product> favoriteProducts = new ArrayList<>();

    @ElementCollection
    @CollectionTable(schema = "HANDCRAFTED_SCHEMA", name = "PRODUCTS_IN_CART", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "QUANTITY")
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    private Map<Product, Long> productsInCart = new HashMap<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    public User(Long id, String userName, String name, String surname, String password) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
