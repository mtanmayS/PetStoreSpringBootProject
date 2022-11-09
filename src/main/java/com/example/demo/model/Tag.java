package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("tag")
public class Tag {

    @Id
   @Column("tag_id")
    private long id;

    @Column("tag_name")
    private String name;
}
