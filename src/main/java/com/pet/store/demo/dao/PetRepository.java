package com.pet.store.demo.dao;

import com.pet.store.demo.model.Category;
import com.pet.store.demo.model.Pet;
import com.pet.store.demo.model.PetPhotoUrl;
import com.pet.store.demo.model.Tag;
import com.pet.store.demo.request.PetRequest;
import com.pet.store.demo.response.PetResponse;

public interface PetRepository {

    PetResponse savePet(PetRequest pet);
    PetResponse updatePet(PetRequest pet);
    PetResponse getPetById(long id);
    String deletePetById(long id);



}
