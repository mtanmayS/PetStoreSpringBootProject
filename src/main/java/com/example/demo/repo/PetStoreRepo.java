package com.example.demo.repo;

import com.example.demo.model.Pet;
import com.example.demo.model.StatusEnum;
import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetStoreRepo extends JpaRepository<Pet, Long> {

    Optional<List<Pet>> findByStatus(StatusEnum status);

    Optional<List<Pet>> findByTags(Tag tags);




}
