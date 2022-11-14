package com.pet.store.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@Table("tag")
public class Tag {

    @Id
   @Column("tag_id")
    private long id;
    @Id
    @Column("pet_id")
    private long petId;
    @Column("tag_name")
    private String name;
}
