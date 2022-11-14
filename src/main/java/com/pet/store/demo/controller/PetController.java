package com.pet.store.demo.controller;

import com.pet.store.demo.dao.PetRepositoryImpl;
import com.pet.store.demo.model.Pet;
import com.pet.store.demo.request.PetRequest;
import com.pet.store.demo.response.PetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetRepositoryImpl petRepository;


    @PostMapping( )
    public ResponseEntity<PetResponse> savePet(@RequestBody PetRequest petRequest){
        return new ResponseEntity<PetResponse>(petRepository.savePet(petRequest), HttpStatus.OK);


    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable(value = "petId", required = true) long id){
        return new ResponseEntity<>(petRepository.deletePetById(id), HttpStatus.OK);



    }
    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> getPet(@PathVariable(value = "petId",required = true) long id){
        return new ResponseEntity<PetResponse>(petRepository.getPetById(id), HttpStatus.FOUND);

    }

    @PutMapping
    public ResponseEntity<PetResponse> updatePetById(@RequestBody PetRequest petRequest){
        return new ResponseEntity<PetResponse>(petRepository.updatePet(petRequest), HttpStatus.OK);

    }

    @PostMapping("/{petId}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable("petId") long id,@RequestParam String name, @RequestParam
    String status){

        return new ResponseEntity<PetResponse>(petRepository.updatePet(id,name,status), HttpStatus.OK);


    }


    @GetMapping("/findByTags")
    public ResponseEntity<List<PetResponse>> getPetByTags(@RequestParam(required = true) List<String> tags){
        return new ResponseEntity<List<PetResponse>>(petRepository.fetchPetByTags(tags), HttpStatus.FOUND);

    }


}

