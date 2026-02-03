package dev.jay.productservices.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Soft delete:
// Instead of physically deleting the record from DB,
// Hibernate will update is_deleted = true
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE id=?")

// Automatically filters deleted rows in ALL queries
// SELECT * FROM product WHERE is_deleted = false
@Where(clause = "is_deleted = false")
public class Product extends BaseModel {

    private String title;

    private String description;

    private double price;

    private String imageUrl;


    /*
     CascadeType.PERSIST:
     If a new product is created with a category that does not exist,
     Hibernate will first persist the category automatically.

     If the category already exists, it will simply associate it.

     This prevents foreign key violations while creating products.
    */
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;


    /*
     Future enhancement:
     You can add quantity/stock fields for inventory management.
     Example:
     private int quantity;
    */
}
