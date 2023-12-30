package com.annak.handcrafted.entity;

public enum TypeOfReceipt {
    PICKUP_FROM_THE_STORE("Pickup from the Store"),
    DELIVERY_TO_THE_POST_OFFICE("Delivery to the Post Office");

    private final String name;

    TypeOfReceipt(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
