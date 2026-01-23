package dev.jay.productservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Category extends BaseModel {
    private String title;

//    REMOVE used if a person deletes the category it will also the delete the products related to it.
    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Product> products;
}
