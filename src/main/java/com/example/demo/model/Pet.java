package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


import java.util.List;

@Data
@Table("pet")
public class Pet {


    @Id
    @Column("pet_id")
    private long id;
    @Column("pet_name")
    private String name;
    @Column("pet_status")
    private StatusEnum status;

    @Column("pet_category")
    private Category category;

    @Column("pet_photo_urls")
    private List<String> photoUrls;


    @Column("pet_tags")
    private List<Tag> tags;

}
