package com.example.demo.repo;


import com.example.demo.model.Category;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCategoryRepo extends CrudRepository<Category,Long> {
}
