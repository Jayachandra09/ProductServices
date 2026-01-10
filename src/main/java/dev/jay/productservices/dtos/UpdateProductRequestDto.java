package dev.jay.productservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequestDto {
    private String title;
    private String description;
    private double price;
    private String category;
    private String image;
}
