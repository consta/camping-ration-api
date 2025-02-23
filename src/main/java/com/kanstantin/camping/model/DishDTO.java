package com.kanstantin.camping.model;

import java.util.List;

import lombok.Data;

@Data
public class DishDTO {
    private Integer id;
    private String name;
    private String description;
    private List<IngredientDTO> ingredients;
}
