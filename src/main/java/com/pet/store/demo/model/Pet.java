package com.pet.store.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Table("pet")
public class Pet {


    @Id
    @Column("pet_id")
    private long id;
    @Column("pet_name")
    private String name;
    @Column("pet_status")
    private String status;


}
