package com.pet.store.demo.response;


import com.pet.store.demo.model.OrderStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private long id;
    private long petId;
    private long quantity;
    private LocalDateTime shipDate;
    private OrderStatusEnum status;
    private Boolean complete;

}
