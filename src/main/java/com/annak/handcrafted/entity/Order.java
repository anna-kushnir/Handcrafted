package com.annak.handcrafted.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "ORDER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ORDER", schema = "HANDCRAFTED_SCHEMA")
public class Order {
    @Id
    @SequenceGenerator(name = "ID_GENERATOR_ORDER", sequenceName = "HANDCRAFTED_SCHEMA.ORDER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_ORDER")
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "USER_PHONE")
    private Long userPhone;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "FORMATION_DATE")
    private LocalDateTime formationDate = LocalDateTime.now();

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TYPE_OF_RECEIPT")
    private String typeOfReceipt;

    @Column(name = "RECEIPT_DATE")
    private LocalDateTime receiptDate;

    @AttributeOverrides({
            @AttributeOverride(name = "region", column = @Column(name = "DELIVERY_ADDRESS_REGION")),
            @AttributeOverride(name = "city", column = @Column(name = "DELIVERY_ADDRESS_CITY")),
            @AttributeOverride(name = "postAddress", column = @Column(name = "DELIVERY_ADDRESS_POST_ADDRESS"))
    })
    private DeliveryAddress deliveryAddress;

    @Column(name = "INVOICE_NUMBER")
    private Long invoiceNumber;

    @ElementCollection
    @CollectionTable(name = "PRODUCTS_IN_ORDER", joinColumns = @JoinColumn(name = "ORDER_ID"))
    @Column(name = "QUANTITY")
    @MapKeyJoinColumn(name = "PRODUCT_ID")
    private Map<Product, Long> products = new HashMap<>();
}
