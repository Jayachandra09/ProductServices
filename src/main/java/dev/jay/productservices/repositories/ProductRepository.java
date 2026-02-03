package dev.jay.productservices.repositories;

import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import dev.jay.productservices.repositories.projections.ProductProjection;
import dev.jay.productservices.repositories.projections.ProductWithIdAndTitle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/*
 ======================================================
 Product Repository
 ======================================================

 JpaRepository<Product, Long>

 - Product → Entity type managed by this repository
 - Long    → Type of primary key (id)

 JpaRepository already provides common CRUD operations:
 save, findById, findAll, delete, deleteById, pagination, etc.
*/
public interface ProductRepository extends JpaRepository<Product, Long> {


    /*
     ======================================================
     BASIC CRUD OPERATIONS
     ======================================================
     */


    /*
     Saves the given product entity into the database.

     Behavior:
     - If product.id is NULL  → INSERT operation
     - If product.id exists → UPDATE operation

     Automatically generated fields (like id) will be populated
     in the returned Product object.
     */
    Product save(Product p);


    /*
     Fetches all products from the database.

     NOTE:
     Because we use @Where(is_deleted = false),
     soft deleted products are automatically excluded.
     */
    @Override
    List<Product> findAll();


    /*
     Fetches a single product whose id exactly matches the given value.

     NOTE:
     - Returns NULL if no product is found
     - Caller should handle null safely
     */
    Product findByIdEquals(Long id);


    /*
     Fetches all products that have the given title.

     Useful for:
     - Search
     - Filtering
     - Keyword matching
     */
    List<Product> findAllByTitle(String title);


    /*
     Fetches all products belonging to the given category.

     This relies on the @ManyToOne relationship between
     Product and Category.
     */
    List<Product> findAllByCategory(Category category);


    /*
     Instead of writing multiple queries,
     we can directly access nested attributes using "_" notation.

     Example:
     category.id → category_Id
     */
    List<Product> findAllByCategory_IdEquals(Long categoryId);



    /*
     ======================================================
     HQL / JPQL CUSTOM QUERIES
     ======================================================
     */


    /*
     Fetch a specific product using:
     - category name
     - product id

     Uses JPQL (HQL) query.
     */
    @Query("select p from Product p where p.category.title = :categoryName and p.id = :productId")
    Product productWithSpecificCategoryName(@Param("categoryName") String categoryName,
                                            @Param("productId") Long productId);


    /*
     Projection query:
     Returns only selected fields (id and title),
     instead of the full Product object.

     Improves performance when full entity data is not required.
     */
    @Query("select p.id as id, p.title as title from Product p where p.category.id = :categoryId")
    List<ProductProjection> getTitlesOfProductForGivenCategory(@Param("categoryId") Long categoryId);



    /*
     ======================================================
     REPORTING / ANALYTICS QUERIES
     ======================================================

     Used by cron scheduler to calculate statistics.
     */


    /*
     Calculates average price of all active products.

     Used for daily analytics report generation.
     */
    @Query("SELECT AVG(p.price) FROM Product p")
    Double findAveragePrice();



    /*
     ======================================================
     SOFT DELETE SUPPORT QUERIES
     ======================================================

     Because we use:
     @Where(is_deleted = false)

     Normal queries automatically hide deleted records.

     To manage deleted data (restore/view),
     we must use native queries to bypass the filter.
     */


    /*
     Fetch a product even if it is soft deleted.

     Native query bypasses @Where clause.
     Useful for restore operations.
     */
    @Query(value = "SELECT * FROM product WHERE id = :id", nativeQuery = true)
    Product findByIdIncludingDeleted(Long id);


    /*
     Restore a single soft deleted product.

     Instead of deleting permanently,
     we update is_deleted = false.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET is_deleted = false WHERE id = :id", nativeQuery = true)
    void restoreProduct(Long id);



    /*
     ======================================================
     ADMIN OPERATIONS
     ======================================================
     */


    /*
     Fetch ONLY soft deleted products.

     Useful for:
     - Admin dashboard
     - Restore operations
     - Audit or recovery
     */
    @Query(value = "SELECT * FROM product WHERE is_deleted = true", nativeQuery = true)
    List<Product> findDeletedProducts();


    /*
     Restore ALL soft deleted products in bulk.

     Returns:
     number of rows updated.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET is_deleted = false WHERE is_deleted = true", nativeQuery = true)
    int restoreAllProducts();
}
