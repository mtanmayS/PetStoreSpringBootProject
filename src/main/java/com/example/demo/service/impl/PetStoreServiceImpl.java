package com.example.demo.service.impl;

import com.example.demo.bean.request.PetRequest;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.NoDataFoundException;
import com.example.demo.model.*;
import com.example.demo.repo.PetCategoryRepo;
import com.example.demo.repo.PetStoreRepo;
//import com.example.demo.repo.PhotoRepo;
import com.example.demo.repo.PhotoTagRepo;
import com.example.demo.service.PetStoreService;
import com.example.demo.utils.CopyNonNullPropertiesUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetStoreServiceImpl implements PetStoreService {

    @Autowired
    private PetStoreRepo petStoreRepo;

    @Autowired
    PetCategoryRepo petCategoryRepo;


    @Autowired
    PhotoTagRepo photoTagRepo;

    ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Override
    public Pet savePet(PetRequest petRequest) {

        Category category=petRequest.getCategory();
        petCategoryRepo.save(category);
        List<Tag> tagList= petRequest.getTags();
        for (Tag tag: tagList){
            photoTagRepo.save(tag);
        }
        Pet pet=objectMapper.convertValue(petRequest,Pet.class);
        return petStoreRepo.save(pet);

    }

    @Override
    public Pet fetchPet(int id) {
        Optional<Pet> optionalPet= petStoreRepo.findById(Long.valueOf(id));
        Pet pet= new Pet();
        if(optionalPet.isPresent()){
            pet= optionalPet.get();
            return pet;
        }else{
            throw new InvalidDataException("Invalid Id");
        }

    }

    @Override
    public String deletePet(int id) {
        try{
            petStoreRepo.deleteById((long) id);
            return "Pet Deleted";

        }catch (Exception e){
            throw new  InvalidDataException("Invalid Pet value");
        }


    }

    public List<Pet> fetchPetByStatus(List<String> statusList){
        List<Pet> petList= new ArrayList<>();
        try{
            for(String string: statusList){
                Optional<List<Pet>> pets=petStoreRepo.findByStatus(StatusEnum.valueOf(string));
                pets.ifPresent(pet->petList.addAll(pet));
                // petList.addAll(pets.get());

            }
            return petList;
        }catch (Exception e){
            throw new InvalidDataException("Invalid Status value");
        }



    }

    public List<Pet> fetchPetByTags(List<String> tagsList){
        List<Tag> tags= new ArrayList<>();
        List<Pet> petList= new ArrayList<>();

            for (String tag : tagsList) {
                Optional<List<Tag>> optionalTag = photoTagRepo.findByName(tag);
                //optionalTag.ifPresent(tagLists -> tags.addAll(tagLists));
                if(optionalTag.isPresent()){
                    tags.addAll(optionalTag.get());
                }else{
                    throw  new InvalidDataException("Invalid Tag Value");
                }
            }


            for (Tag tag : tags) {
                Optional<List<Pet>> optionalPets = petStoreRepo.findByTags(tag);
                //optionalPets.ifPresent(pet -> petList.addAll(pet));
                if(optionalPets.isPresent()){
                    petList.addAll( optionalPets.get());
                }else{
                    throw  new InvalidDataException("No Pet found with the specified tag");
                }
            }
            return petList;




    }

    public Pet updatePetById(PetRequest petRequest){

        Optional<Pet> optionalPet=petStoreRepo.findById(petRequest.getId());
        Pet pet = new Pet();
        if(optionalPet.isPresent()){
        if(petRequest.getCategory()!=null){
            Category category=petRequest.getCategory();
            petCategoryRepo.save(category);
        }
        if(!petRequest.getTags().isEmpty()){
            List<Tag> tagList= petRequest.getTags();
            for (Tag tag: tagList){
                photoTagRepo.save(tag);
            }
        }

            pet= optionalPet.get();
            CopyNonNullPropertiesUtil.copyNonNullProperties(petRequest,pet );


            pet= petStoreRepo.save(pet);
            return pet;
        }else {
            throw new NoDataFoundException("Pet Not found");
        }

    }

    public Pet updatePet(int id,String name, String status){

        Optional<Pet> optionalPet=petStoreRepo.findById((long) id);
        Pet pet= new Pet();
        if(optionalPet.isPresent()){
            pet= optionalPet.get();
            if(name!=null){
                pet.setName(name);
            }
            if(status!=null){
                pet.setStatus(StatusEnum.valueOf(status));
            }
            return  petStoreRepo.save(pet);
        }else{
            throw  new NoDataFoundException("Pet not found");
        }


    }


}
