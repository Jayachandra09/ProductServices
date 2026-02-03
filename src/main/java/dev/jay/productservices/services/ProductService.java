package dev.jay.productservices.services;

import dev.jay.productservices.dtos.CreateProductRequestDto;
import dev.jay.productservices.exceptions.ProductNotFoundException;
import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getProducts();

    List<Category> getCategories();

    Product createProduct(String title,
                          String description,
                          double price,
                          String category,
                          String image
    );

    Product deleteProduct(Long productId);

    Product updateProduct(Long productId,
                          String title,
                          String description,
                          double price,
                          String category,
                          String image
    );

    List<Product> getProductByCategory(String category);

    Page<Product> getProductByPagination(Integer pageSize, Integer pageNumber, String sort);

    Product restoreProduct(Long id);
}
