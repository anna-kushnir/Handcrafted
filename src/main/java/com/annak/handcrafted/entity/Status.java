package com.annak.handcrafted.entity;

public enum Status {
    IN_PROCESSING("In processing"),
    ACCEPTED("Accepted"),
    FORWARDED_FOR_DELIVERY("Forwarded for delivery"),
    RECEIVED("Received");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
