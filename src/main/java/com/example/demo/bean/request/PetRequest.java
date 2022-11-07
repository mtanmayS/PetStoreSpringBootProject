package com.example.demo.bean.request;

import com.example.demo.model.Category;
import com.example.demo.model.StatusEnum;
import com.example.demo.model.Tag;
import lombok.Data;


import java.util.List;

@Data
public class PetRequest {


    private long id;
    private String name;
    private StatusEnum status;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;
}
