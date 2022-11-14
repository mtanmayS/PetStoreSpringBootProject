package com.pet.store.demo.request;

import com.pet.store.demo.model.OrderStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class OrderRequest {

    private long petId;
    private long quantity;
    private LocalDateTime shipDate;
    private OrderStatusEnum status;
    private Boolean complete;
}
