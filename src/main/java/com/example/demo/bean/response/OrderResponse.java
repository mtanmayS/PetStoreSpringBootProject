package com.example.demo.bean.response;

import com.example.demo.model.OrderStatusEnum;
import lombok.Data;

@Data
public class OrderResponse {
    private long id;
    private long petId;
    private long quantity;
    private java.util.Date shipDate;
    private OrderStatusEnum status;
    private Boolean complete;

}
