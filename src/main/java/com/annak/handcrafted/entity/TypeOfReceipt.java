package com.annak.handcrafted.entity;

public enum TypeOfReceipt {
    PICKUP_FROM_THE_STORE(1, "Pickup from the Store"),
    DELIVERY_TO_THE_POST_OFFICE(2, "Delivery to the Post Office");

    private final int id;
    private final String name;

    TypeOfReceipt(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
