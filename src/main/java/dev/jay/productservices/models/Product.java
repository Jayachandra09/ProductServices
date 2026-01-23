package dev.jay.productservices.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel{
    private String title;
    private String description;
    private double price;
    private String imageUrl;

//    CascadeType.PERSIST is used for when a person is adding a product with a category id
//    If that category does not exist in category table it will throw to user to add a category first and then product
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;

}
