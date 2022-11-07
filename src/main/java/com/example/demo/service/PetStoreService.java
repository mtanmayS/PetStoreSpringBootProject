package com.example.demo.service;

import com.example.demo.bean.request.PetRequest;
import com.example.demo.model.Pet;

public interface PetStoreService {



    public Pet savePet(PetRequest petRequest);

    public Pet fetchPet(int id);

    public String deletePet(int id);
}
