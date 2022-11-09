package com.example.demo.repo;

import com.example.demo.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoTagRepo extends CrudRepository<Tag,Long> {

    Optional<List<Tag>> findByName(String name);
}
