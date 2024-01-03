package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.*;
import com.annak.handcrafted.entity.embedded.DeliveryAddress;
import com.annak.handcrafted.entity.embedded.Status;
import com.annak.handcrafted.entity.embedded.TypeOfReceipt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private User user;
    private Long userPhone;
    private BigDecimal price;
    private LocalDateTime formationDate;
    private Status status;
    private TypeOfReceipt typeOfReceipt;
    private LocalDateTime receiptDate;
    private DeliveryAddress deliveryAddress;
    private Long invoiceNumber;
}
