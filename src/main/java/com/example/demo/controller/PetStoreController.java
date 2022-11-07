package com.example.demo.controller;



import com.example.demo.bean.request.PetRequest;
import com.example.demo.model.Pet;
import com.example.demo.service.impl.PetStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetStoreController {


    @Autowired
    PetStoreServiceImpl petStoreService;

    @PostMapping( )
    public ResponseEntity<Pet> savePet(@RequestBody PetRequest petRequest){
        return new ResponseEntity<Pet>(petStoreService.savePet(petRequest), HttpStatus.OK);


    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable(value = "petId",required = true) int id){
        return new ResponseEntity<Pet>(petStoreService.fetchPet(id), HttpStatus.FOUND);

    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable(value = "petId", required = true) int id){
            return new ResponseEntity<>(petStoreService.deletePet(id), HttpStatus.OK);



    }

    @GetMapping("/findByStatus")
    public ResponseEntity<List<Pet>> getPetByStatus(@RequestParam(required = true) List<String> status){
        return new ResponseEntity<List<Pet>>(petStoreService.fetchPetByStatus(status), HttpStatus.FOUND);

    }

    @GetMapping("/findByTags")
    public ResponseEntity<List<Pet>> getPetByTags(@RequestParam(required = true) List<String> tags){
        return new ResponseEntity<List<Pet>>(petStoreService.fetchPetByTags(tags), HttpStatus.FOUND);

    }

    @PutMapping
    public ResponseEntity<Pet> updatePetById(@RequestBody PetRequest petRequest){
        return new ResponseEntity<Pet>(petStoreService.updatePetById(petRequest), HttpStatus.OK);

    }

    @PostMapping("/{petId}")
    public ResponseEntity<Pet> updatePet(@PathVariable("petId") int id,@RequestParam String name, @RequestParam
                                         String status){

        return new ResponseEntity<Pet>(petStoreService.updatePet(id,name,status), HttpStatus.OK);


    }


}
