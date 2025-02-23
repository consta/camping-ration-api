package com.kanstantin.camping.model;

import lombok.Data;

@Data
public class IngredientDTO {
    private Integer id; // ID of the ingredient (DishIngredientEntity)
    private ProductDTO product; // Reference to ProductEntity
    private String comment;
    private Float amount;
}
