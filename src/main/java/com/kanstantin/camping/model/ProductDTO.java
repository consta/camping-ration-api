package com.kanstantin.camping.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private Float calories;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private List<CategoryDTO> categories;
}
