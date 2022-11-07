package com.example.demo.bean.request;

import com.example.demo.model.OrderStatusEnum;
import com.example.demo.model.Pet;
import lombok.Data;


@Data
public class OrderRequest {

    private int petId;
    private long quantity;
    private java.util.Date shipDate;
    private OrderStatusEnum status;
    private Boolean complete;
}
