package dev.jay.productservices.dtos;

import dev.jay.productservices.models.Category;
import dev.jay.productservices.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        Category productCategory = new Category();
        productCategory.setTitle(category);

        product.setCategory(productCategory);
        return product;
    }
}
