package dev.jay.productservices.controllers;

import dev.jay.productservices.dtos.CreateProductRequestDto;
import dev.jay.productservices.dtos.ErrorDto;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    private RestTemplate restTemplate;

    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    @CachePut(value = "product", key="#result.id", unless = "#result.category == null ")
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

    @Cacheable(value = "product")
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return productService.getSingleProduct(productId);
    }

//    @Cacheable(value = "products")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> products = productService.getProducts();

//        Manually throwing error
//        throw new RuntimeException();
//         HttpStatus.NOT_FOUND -> Manually changing the 200 response to show it as 404 by using ResponseEntity
        ResponseEntity<List<Product>> response= new ResponseEntity<>(products, HttpStatus.OK);

        return response;
    }

//    @Cacheable(value = "products")
    @GetMapping("/products/categories")
    public List<Category> getCategories() {
        return productService.getCategories();
    }

    @CachePut(value = "product", key = "#result.id", unless = "#result.category == null")
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

    @CacheEvict(value = "product", key = "#result.id")
    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId) {
        return productService.deleteProduct(productId);
    }

//    @Cacheable(value = "products")
    @GetMapping("/products/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable("categoryName") String categoryName) {
        return productService.getProductByCategory(categoryName);
    }


////    Creating function for Product not found exception
////    If this controller ever throws a ProductNotFound exception for any reason don't throw the exception as it is
////    (Controller Advice) this is not a good method to show the exception to client...Java will throw the all the reson for exception
////    Instead we are calling this method to what actually we want to show the exception
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException exception) {
//
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(exception.getMessage());
//
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }
//    For Best practices we are creating advices moving code to there



//    -----------------------------
//    Pagination and Sorting
//    _______________________________
    @GetMapping("/products/{pageSize}/{pageNumber}")
    public ResponseEntity getProductsByPage(@PathVariable("pageSize") int pageSize,
                                            @PathVariable("pageNumber") int pageNumber) {

        Page<Product> productsByPage = productService.getProductByPagination(pageSize, pageNumber, null);
        return ResponseEntity.ok(productsByPage.getContent());
    }


    @GetMapping("/productsByPrice/{pageSize}/{pageNumber}")
    public ResponseEntity getProductsByPageSortByPrice(@PathVariable("pageSize") int pageSize,
                                            @PathVariable("pageNumber") int pageNumber) {

        Page<Product> productsByPageSortByPrice = productService.getProductByPagination(pageSize, pageNumber, "price");
        return ResponseEntity.ok(productsByPageSortByPrice.getContent());
    }

    @GetMapping("/productsByTitle/{pageSize}/{pageNumber}")
    public ResponseEntity getProductsByPageSortByTitle(@PathVariable("pageSize") int pageSize,
                                                       @PathVariable("pageNumber") int pageNumber) {
        Page<Product> productsByPageSortByTitle = productService.getProductByPagination(pageSize, pageNumber, "title");
        return ResponseEntity.ok(productsByPageSortByTitle.getContent());
    }

    @GetMapping("/productsByCategory/{pageSize}/{pageNumber}")
    public ResponseEntity getProductsByPageSortByCategory(@PathVariable("pageSize") int pageSize,
                                                          @PathVariable("pageNumber") int pageNumber) {
        Page<Product> productsByPageSortBycategory = productService.getProductByPagination(pageSize, pageNumber, "category_id");
        return ResponseEntity.ok(productsByPageSortBycategory.getContent());
    }

}
