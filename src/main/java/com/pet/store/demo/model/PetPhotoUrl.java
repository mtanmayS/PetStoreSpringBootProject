package com.pet.store.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
@Data
@AllArgsConstructor
@Table("petPhotoUrl")
public class PetPhotoUrl {

    @Id
    @Column("photo_url_id")
    private long id;
    @Id
    @Column("pet_id")
    private long petId;
    @Column("photo_url")
    private String name;
}
