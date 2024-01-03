package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.embedded.TypeOfReceipt;
import com.annak.handcrafted.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDto {
    private User user;
    private Long userPhone;
    private TypeOfReceipt typeOfReceipt;
    private String deliveryAddressRegion;
    private String deliveryAddressCity;
    private String deliveryAddressPostAddress;
}
