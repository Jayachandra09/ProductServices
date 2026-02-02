package dev.jay.productservices;

import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.repositories.CategoryRepository;
import dev.jay.productservices.repositories.ProductRepository;
import dev.jay.productservices.repositories.projections.ProductProjection;
import dev.jay.productservices.repositories.projections.ProductWithIdAndTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServicesApplicationTests {
   @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public

    @Test
    void contextLoads() {
    }

//    For practice
    @Test
    void testingQueries() {
//        productRepository.findAllByTitle("Jay");
//        productRepository.findByIdEquals(1L);

//        List<ProductProjection> pros = productRepository.getTitlesOfProductForGivenCategory(
//                2L
//        );
//        System.out.println(pros.get(0).getId());
//        System.out.println(pros.get(0).getTitle());

//        Optional<Category> optionalCategory = categoryRepository.findById(
//                1L
//        );
//
//        Category category = optionalCategory.get();
//        System.out.println("Fetched Category objects");
//
//        List<Product> products = optionalCategory.get().getProducts();
//        System.out.println("Fetched Product from Category objects");


    }
}
