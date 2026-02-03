package dev.jay.productservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

// Soft delete for category
// DELETE will become UPDATE is_deleted = true
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE id=?")

// Hide deleted categories automatically
@Where(clause = "is_deleted = false")
public class Category extends BaseModel {

    private String title;


    /*
     CascadeType.REMOVE:
     If a category is deleted, all its related products will also be deleted.

     Since we are now using soft delete,
     Hibernate will mark them as is_deleted = true instead of physically deleting.

     FetchType.EAGER:
     Products are loaded immediately when category is fetched.
     (Use LAZY in large-scale systems for better performance)
    */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "category",
            cascade = CascadeType.REMOVE
    )
    @JsonIgnore
    private List<Product> products;
}
