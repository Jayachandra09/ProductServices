package dev.jay.productservices.repositories;

import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.repositories.projections.ProductProjection;
import dev.jay.productservices.repositories.projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * JpaRepository<Product, Long>
 *
 * - Product   → Entity type this repository manages
 * - Long      → Type of primary key (id)
 *
 * JpaRepository already provides common CRUD operations such as:
 * save, findById, findAll, delete, deleteById, etc.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /*
     * Saves the given product entity into the database.
     *
     * - If product.id is NULL → INSERT operation
     * - If product.id exists → UPDATE operation
     *
     * Automatically generated fields (like id) will be populated
     * in the returned Product object.
     */
    Product save(Product p);

    /*
     * Fetches all products from the database.
     * This method is already available in JpaRepository,
     * but overridden here explicitly for clarity.
     */
    @Override
    List<Product> findAll();

    /*
     * Fetches a single product whose id exactly matches the given value.
     *
     * Note:
     * - Returns NULL if no product is found
     * - Caller must handle null safely
     */
    Product findByIdEquals(Long id);

    /*
     * Fetches all products having the given title.
     * Useful for search or filtering use cases.
     */
    List<Product> findAllByTitle(String title);

    /*
     * Fetches all products that belong to the given category.
     *
     * This relies on the @ManyToOne relationship between
     * Product and Category.
     */
    List<Product> findAllByCategory(Category category);

    /*
     * Deletes the given product entity from the database.
     *
     * The product object must be a managed entity
     * (i.e., fetched from the database before deletion).
     */
//    Product delete(Product product);


//    Instead of doing 2 queries by using _ we can define the attributes in that entity
    List<Product> findAllByCategory_IdEquals(Long categoryId);


//    HQL Queries

    @Query("select p from Product p where p.category.title = :categoryName and p.id = :productId")
    Product productWithSpecificCategoryName(@Param("categoryName") String categoryName,
                                            @Param("productId") Long productId);

    @Query("select p.id as id, p.title as title from Product p where p.category.id = :categoryId")
    List<ProductProjection> getTitlesOfProductForGivenCategory(@Param("categoryId") Long categoryId);
}
