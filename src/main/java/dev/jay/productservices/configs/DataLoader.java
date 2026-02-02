package dev.jay.productservices.configs;

import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.repositories.CategoryRepository;
import dev.jay.productservices.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class DataLoader {

    @Bean
    @Transactional
    ApplicationRunner loadData(ProductRepository productRepository,
                               CategoryRepository categoryRepository) {

        return args -> {

            if (categoryRepository.count() > 0) return;

            System.out.println("Seeding database with sample products...");

            List<String> categoryNames = List.of(
                    "Electronics", "Clothing", "Books", "Home",
                    "Sports", "Beauty", "Toys", "Groceries"
            );

            Map<String, Category> categoryMap = new HashMap<>();

            // Save categories first and FLUSH
            for (String name : categoryNames) {
                Category c = new Category();
                c.setTitle(name);
                categoryMap.put(name, categoryRepository.save(c));
            }

            categoryRepository.flush();

            List<Product> products = new ArrayList<>();

            Map<String, List<String>> productsMap = Map.of(
                    "Electronics", List.of("iPhone 15","Samsung S24","MacBook Pro","AirPods","Smart Watch"),
                    "Clothing", List.of("T-Shirt","Jeans","Jacket","Sneakers","Cap"),
                    "Books", List.of("Spring Boot Guide","Java Basics","DSA Mastery","Microservices Book"),
                    "Home", List.of("Sofa","Chair","Dining Table","Lamp"),
                    "Sports", List.of("Football","Cricket Bat","Tennis Racket","Gym Dumbbells"),
                    "Beauty", List.of("Face Wash","Shampoo","Perfume"),
                    "Toys", List.of("Toy Car","Puzzle","Board Game"),
                    "Groceries", List.of("Rice Bag","Cooking Oil","Chocolate","Biscuits")
            );

            for (var entry : productsMap.entrySet()) {
                Category category = categoryMap.get(entry.getKey());

                for (String name : entry.getValue()) {
                    Product p = new Product();
                    p.setTitle(name);
                    p.setDescription(name + " description");
                    p.setImageUrl(name.toLowerCase().replace(" ", "_") + ".jpg");
                    p.setPrice((double) ThreadLocalRandom.current().nextInt(100, 50000));
                    p.setCategory(category);

                    products.add(p);
                }
            }

            productRepository.saveAll(products);

            System.out.println("Seed data inserted successfully!");
        };
    }
}
