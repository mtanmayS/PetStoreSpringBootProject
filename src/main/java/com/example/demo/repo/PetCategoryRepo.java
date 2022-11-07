package com.example.demo.repo;

import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetCategoryRepo extends JpaRepository<Category,Long> {
}
