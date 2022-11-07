package com.example.demo.repo;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoTagRepo extends JpaRepository<Tag,Long> {

    Optional<List<Tag>> findByName(String name);
}
