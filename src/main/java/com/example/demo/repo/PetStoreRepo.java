package com.example.demo.repo;

import com.example.demo.model.Pet;
import com.example.demo.model.StatusEnum;
import com.example.demo.model.Tag;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PetStoreRepo extends CrudRepository<Pet,Long> {


    Optional<List<Pet>> findByStatus(StatusEnum status);

    @Query("select * from Pet p where p.tags=:tags")
    Optional<List<Pet>> findByTags(Tag tags);




}
