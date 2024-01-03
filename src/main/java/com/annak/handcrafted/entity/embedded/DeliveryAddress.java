package com.annak.handcrafted.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = {"region", "city", "postAddress"})
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DeliveryAddress {
    private String region;
    private String city;
    private String postAddress;

    @Override
    public String toString() {
        return region + ", " + city + ", " + postAddress;
    }
}
