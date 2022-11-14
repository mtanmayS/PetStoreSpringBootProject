package com.pet.store.demo.request;


import com.pet.store.demo.model.Category;
import com.pet.store.demo.model.StatusEnum;
import com.pet.store.demo.model.Tag;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PetRequest {


    private long id;
    private String name;
    private StatusEnum status;
    private PetCategory category;
    private List<String> photoUrls;
    private Set<Tags> tags;
}
