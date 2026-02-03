package dev.jay.productservices.services;

import dev.jay.productservices.exceptions.ProductNotFoundException;
import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.repositories.CategoryRepository;
import dev.jay.productservices.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


/*
 ======================================================
 SelfProductService
 ======================================================

 This is the primary business logic layer for Product APIs.

 Responsibilities:
 - Handle product CRUD operations
 - Manage category creation automatically
 - Support pagination & sorting
 - Implement soft delete restore
 - Interact only with repositories (DB layer)

 NOTE:
 Controllers should never directly access repositories.
 They must go through this service layer.
*/
@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    /*
     ======================================================
     FETCH OPERATIONS
     ======================================================
     */


    /*
     Returns a single product by id.

     Throws:
     ProductNotFoundException if not found.
     */
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdEquals(productId);
    }


    /*
     Fetch all active (non-deleted) products.

     Soft deleted records are automatically excluded
     due to @Where filter in the entity.
     */
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    /*
     Fetch all categories.
     */
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }



    /*
     ======================================================
     CREATE OPERATIONS
     ======================================================
     */


    /*
     Creates a new product.

     Steps:
     1. Build Product object
     2. Check if category exists
     3. If not → create new category
     4. Save product

     This avoids foreign key issues and ensures
     categories are always valid.
     */
    @Override
    public Product createProduct(String title,
                                 String description,
                                 double price,
                                 String category,
                                 String image) {

        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category categoryFromDatabase = categoryRepository.findByTitle(category);

        /*
         If the category does not exist,
         create and persist a new category automatically.
        */
        if (categoryFromDatabase == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);
            categoryFromDatabase = categoryRepository.save(newCategory);
        }

        /*
         If category exists → it already has an ID
         If new → saved above and now has ID
        */
        product.setCategory(categoryFromDatabase);

        return productRepository.save(product);
    }



    /*
     ======================================================
     DELETE OPERATIONS
     ======================================================

     NOTE:
     Because we implemented soft delete using @SQLDelete,
     calling delete() will NOT physically remove the row.
     Instead, is_deleted = true will be updated.
     */


    /*
     Soft deletes the product.

     Internally converts to:
     UPDATE product SET is_deleted = true
     */
    @Override
    public Product deleteProduct(Long productId) {
        Product product = productRepository.findByIdEquals(productId);

        productRepository.delete(product);

        return product;
    }



    /*
     ======================================================
     UPDATE OPERATIONS
     ======================================================
     */


    /*
     Updates product fields selectively.

     Only non-null values are updated.
     This allows partial updates (PATCH-like behavior).
     */
    @Override
    public Product updateProduct(Long productId,
                                 String title,
                                 String description,
                                 double price,
                                 String category,
                                 String image) {

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

        /*
         Update category only if provided.
         If category does not exist → create new one.
        */
        if (category != null) {
            Category categoryFromDatabase = categoryRepository.findByTitle(category);

            if (categoryFromDatabase == null) {
                Category newCategory = new Category();
                newCategory.setTitle(category);
                categoryFromDatabase = categoryRepository.save(newCategory);
            }

            product.setCategory(categoryFromDatabase);
        }

        if (image != null) {
            product.setImageUrl(image);
        }

        return productRepository.save(product);
    }



    /*
     ======================================================
     FILTER OPERATIONS
     ======================================================
     */


    /*
     Returns products belonging to a given category.

     If category does not exist → returns empty list.
     */
    @Override
    public List<Product> getProductByCategory(String categoryTitle) {

        Category category = categoryRepository.findByTitle(categoryTitle);

        if (category == null) {
            return List.of();
        }

        return productRepository.findAllByCategory(category);
    }



    /*
     ======================================================
     PAGINATION & SORTING
     ======================================================

     pageSize   → number of records per page
     pageNumber → zero-based page index
     sort       → column name for sorting (optional)
     */


    public Page<Product> getProductByPagination(Integer pageSize,
                                                Integer pageNumber,
                                                String sort) {

        Pageable pageable;

        /*
         If sort column is provided → apply sorting
         Else → only pagination
        */
        if (sort != null) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return productRepository.findAll(pageable);
    }



    /*
     ======================================================
     SOFT DELETE RESTORE OPERATIONS
     ======================================================
     */


    /*
     Restores a soft deleted product.

     Steps:
     1. Fetch even deleted product using native query
     2. Update is_deleted = false
     3. Return restored entity
     */
    @Transactional
    public Product restoreProduct(Long id) {

        Product product = productRepository.findByIdIncludingDeleted(id);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        productRepository.restoreProduct(id);

        return productRepository.findById(id).orElseThrow();
    }
}
