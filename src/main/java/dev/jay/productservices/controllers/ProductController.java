package dev.jay.productservices.controllers;

import dev.jay.productservices.dtos.CreateProductRequestDto;
import dev.jay.productservices.dtos.UpdateProductRequestDto;
import dev.jay.productservices.exceptions.ProductNotFoundException;
import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;


/*
 ======================================================
 ProductController
 ======================================================

 Responsibilities:
 - Expose REST APIs
 - Delegate business logic to Service layer
 - Handle Redis caching
 - Provide pagination & sorting
 - Support soft delete restore

 NOTE:
 Controller handles only HTTP concerns.
 Business logic stays inside Service layer.
*/
@RestController
@RequestMapping("/products") // 🔥 base path (clean URLs)
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;
    private final RestTemplate restTemplate;

    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }



    /*
     ======================================================
     CREATE
     ======================================================
     */

    /*
     CachePut:
     After creation, store product in Redis cache.
     #result refers to returned Product.
     */
    @CachePut(value = "product", key = "#result.id", unless = "#result.category == null")
    @Operation(summary = "Create new product")
    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequestDto request) {

        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getImage()
        );
    }



    /*
     ======================================================
     READ
     ======================================================
     */

    /*
     Fetch single product by id.

     Cacheable:
     First request → DB
     Next requests → Redis
     */
    @Cacheable(value = "product", key = "#productId")
    @Operation(summary = "Get Details of a Product by it's Id")
    @GetMapping("/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId)
            throws ProductNotFoundException {

        return productService.getSingleProduct(productId);
    }


    /*
     Fetch all active products.
     */
    @Operation(summary = "Get all Products")
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }


    /*
     Fetch all categories.
     */
    @Operation(summary = "Get all Categories")
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return productService.getCategories();
    }



    /*
     ======================================================
     UPDATE
     ======================================================
     */

    /*
     CachePut:
     Refresh cache after update.
     */
    @CachePut(value = "product", key = "#result.id", unless = "#result.category == null")
    @Operation(summary = "Update a Product by Id")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody UpdateProductRequestDto request) {

        return productService.updateProduct(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getImage()
        );
    }



    /*
     ======================================================
     DELETE (SOFT DELETE)
     ======================================================

     CacheEvict:
     Remove product from Redis after deletion.
     */
    @CacheEvict(value = "product", key = "#result.id")
    @Operation(summary = "Delete a product")
    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }



    /*
     ======================================================
     FILTER
     ======================================================
     */

    /*
     Fetch products by category.
     */
    @Operation(summary = "Get Product by Category name")
    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName) {
        return productService.getProductByCategory(categoryName);
    }



    /*
     ======================================================
     PAGINATION & SORTING (INDUSTRY STYLE)
     ======================================================

     Example:
     /products/paginated?page=0&size=10&sort=price
     */

    @Operation(summary = "Get products with pagination")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Page<Product> result =
                productService.getProductByPagination(size, page, sort);

        return ResponseEntity.ok(result);
    }



    /*
     ======================================================
     RESTORE (SOFT DELETE RECOVERY)
     ======================================================
     */

    /*
     Restores a soft deleted product.
     Sets is_deleted = false.
     */
    @Operation(summary = "Restore the products by Id")
    @PutMapping("/restore/{id}")
    public Product restoreProduct(@PathVariable Long id) {
        return productService.restoreProduct(id);
    }
}
