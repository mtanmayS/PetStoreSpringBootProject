package com.pet.store.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@Table("category")
public class Category {

    @Id
    @Column("category_id")
    private long id;
    @Id
    @Column("pet_id")
    private long petId;
    @Column("category_name")
    private String name;


}
