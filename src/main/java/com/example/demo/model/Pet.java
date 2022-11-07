package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="pet_id")
    private long id;
    @Column(name="pet_name")
    private String name;
    @Column(name="pet_status")
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name="pet_category")
    private Category category;

    @ElementCollection
    @CollectionTable(name = "pet_photos", joinColumns = @JoinColumn(name = "id"))
    @Column(name="pet_photo_urls")
    private List<String> photoUrls;

    @OneToMany
    @JoinColumn(name="pet_tags")
    private List<Tag> tags;

}
