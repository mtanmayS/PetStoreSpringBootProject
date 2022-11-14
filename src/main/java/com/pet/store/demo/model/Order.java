package com.pet.store.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Table("order_table")
public class Order {

    @Id
    @JsonProperty("order_id")
    private long id;
    @JsonProperty("order_pet_id")
    private long petId;
    @JsonProperty("quantity")
    private long quantity;
    @JsonProperty( "ship_date")
    private LocalDateTime shipDate;
    @JsonProperty("order_status")
    private String status;
    @JsonProperty("complete_status")
    private Boolean complete;
}
