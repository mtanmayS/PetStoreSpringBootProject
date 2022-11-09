package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Data

public class Order {

    @Id
    @JsonProperty("order_id")
    private long id;


    @JsonProperty("order_pet_id")
    private Pet petId;

    @JsonProperty("quantity")
    private long quantity;


    @JsonProperty( "ship_date")
    private java.util.Date shipDate;


    @JsonProperty("order_status")
    private OrderStatusEnum status;
    @JsonProperty("complete_status")
    private Boolean complete;
}
