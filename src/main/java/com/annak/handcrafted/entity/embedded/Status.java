package com.annak.handcrafted.entity.embedded;

public enum Status {
    IN_PROCESSING(1, "In processing"),
    ACCEPTED(2, "Accepted"),
    FORWARDED_FOR_DELIVERY(3, "Forwarded for delivery"),
    RECEIVED(4, "Received");

    private final int id;
    private final String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
