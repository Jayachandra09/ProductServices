package dev.jay.productservices.repositories;

import dev.jay.productservices.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// In JpaRepository we will mention which data we are working(Product) pk of that data type(Long)
public interface ProductRepository extends JpaRepository<Product, Long> {

//    save will directly save the product to db
    Product save(Product p); // The attributes which are automatically generated will not be in the params
//    but the id(automatically generated) will be present in the return object
}
