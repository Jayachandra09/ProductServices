package dev.jay.productservices.services;

import dev.jay.productservices.dtos.FakeStoreProductDto;
import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {

        FakeStoreProductDto fakeStoreProduct = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );
        return fakeStoreProduct.toProduct();
    }

    @Override
    public List<Product> getProducts() {

        FakeStoreProductDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto dto : response) {
            products.add(dto.toProduct());
        }

        return products;
    }


    @Override
    public List<Category> getCategories() {

        String[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        List<Category> categories = new ArrayList<>();

        for (String categoryName : response) {
            Category category = new Category();
            category.setTitle(categoryName);
            categories.add(category);
        }

        return categories;
    }


    @Override
    public Product createProduct(String title,
                                 String description,
                                 double price,
                                 String category,
                                 String image) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setCategory(category);
        fakeStoreProductDto.setImage(image);

        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products", // POST request Url
                fakeStoreProductDto, // This is the request body
                FakeStoreProductDto.class // data type of response same as getSingleProduct
        );

        return response.toProduct();
    }

    @Override
    public Product deleteProduct(Long productId) {

        FakeStoreProductDto response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

        restTemplate.delete("https://fakestoreapi.com/products/" + productId);

        System.out.println("Deleted product with id: " + productId);

        return response.toProduct();
    }

    @Override
    public Product updateProduct(Long productId,
                                 String title,
                                 String description,
                                 double price,
                                 String category,
                                 String image) {

        FakeStoreProductDto updateDto = new FakeStoreProductDto();
        updateDto.setTitle(title);
        updateDto.setDescription(description);
        updateDto.setPrice(price);
        updateDto.setCategory(category);
        updateDto.setImage(image);

        restTemplate.put(
                "https://fakestoreapi.com/products/" + productId,
                updateDto
        );

        FakeStoreProductDto updatedProduct = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

        System.out.println("Product updated for Value  : " + productId);

        return updatedProduct.toProduct();
    }


    @Override
    public List<Product> getProductByCategory(String category) {

        FakeStoreProductDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductDto[].class
        );

        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto categoryProduct : response) {

            products.add(categoryProduct.toProduct());
        }
        return products;
    }
}
