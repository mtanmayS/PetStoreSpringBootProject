package com.example.demo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_id")
    private long id;

    @OneToOne
    @JoinColumn(name="order_pet_id")
    private Pet petId;

    @Column(name="quantity")
    private long quantity;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ship_date")
    private java.util.Date shipDate;


    @Column(name="order_status")
    private OrderStatusEnum status;
    @Column(name="complete_status")
    private Boolean complete;
}
