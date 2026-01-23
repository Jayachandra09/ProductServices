package dev.jay.productservices.services;

import dev.jay.productservices.exceptions.ProductNotFoundException;
import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.repositories.CategoryRepository;
import dev.jay.productservices.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdEquals(productId);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description, double price, String category, String image) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category categoryFromDatabase = categoryRepository.findByTitle(category);

//      This category does not exist.
        if (categoryFromDatabase == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);
//            categoryFromDatabase = categoryRepository.save(newCategory);
            categoryFromDatabase = newCategory;
        }
//        If category ID is found in db -> category1 will be having ID
//        else: category1 won't have ID
        product.setCategory(categoryFromDatabase);

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public Product deleteProduct(Long productId) {
        Product product = productRepository.findByIdEquals(productId);

        productRepository.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(Long productId, String title, String description, double price, String category, String image) {

        Product product = productRepository.findByIdEquals(productId);

        if (title != null) {
            product.setTitle(title);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (price >= 0) {
            product.setPrice(price);
        }
        if (category != null) {
            Category categoryFromDatabase = categoryRepository.findByTitle(category);
            if (categoryFromDatabase == null) {
                Category newCategory = new Category();
                newCategory.setTitle(category);
                categoryFromDatabase = newCategory;
            }
            product.setCategory(categoryFromDatabase);
        }

        if (image != null) {
            product.setImageUrl(image);
        }

        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @Override
    public List<Product> getProductByCategory(String categoryTitle) {
        Category category = categoryRepository.findByTitle(categoryTitle);

        if (category == null) {
            return List.of();
        }
//        Long categoryId = category.getId();
        return productRepository.findAllByCategory(category);
    }
}
