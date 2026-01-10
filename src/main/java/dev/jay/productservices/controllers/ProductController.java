package dev.jay.productservices.controllers;

import dev.jay.productservices.dtos.CreateProductRequestDto;
import dev.jay.productservices.dtos.UpdateProductRequestDto;
import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.services.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    private RestTemplate restTemplate;

    public ProductController(ProductService productService,
                             RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getImage()
        );
    }

    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) {
        return productService.getSingleProduct(productId);
    }


    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }


    @GetMapping("/products/categories")
    public List<Category> getCategories() {
        return productService.getCategories();
    }


    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId,
                                 @RequestBody UpdateProductRequestDto request) {

        return productService.updateProduct(
                productId,
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getImage()
        );
    }


    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId) {
        return productService.deleteProduct(productId);
    }


    @GetMapping("/products/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable("categoryName") String categoryName) {
        return productService.getProductByCategory(categoryName);
    }
}
