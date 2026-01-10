package dev.jay.productservices.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
//    private Long userId;
}


//Dto for each request so that in future if the request need additional parameters you can easily add
//without impact anything else