package dev.jay.productservices;

import dev.jay.productservices.controllers.ProductController;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.services.FakeStoreProductService;
import dev.jay.productservices.services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProductServicesApplication {
    private ProductService productService;
    private ProductController productController;

    public ProductServicesApplication(ProductService productService,
                                      ProductController productController) {
        this.productService = productService;
        this.productController = productController;
    }

    public static void main(String[] args) {

//        ProductController productController = new ProductController(new FakeStoreProductService());

        SpringApplication.run(ProductServicesApplication.class, args);
    }

}
