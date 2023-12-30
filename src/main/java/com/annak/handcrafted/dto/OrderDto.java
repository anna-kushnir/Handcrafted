package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Long userPhone;
    private LocalDateTime formationDate;
    private TypeOfReceipt typeOfReceipt;
    private DeliveryAddress deliveryAddress;
}
