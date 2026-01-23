package dev.jay.productservices.repositories;

import dev.jay.productservices.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByTitle(String title);

    Category save(Category category);

//    To find all categories
    List<Category> findAll();


}
