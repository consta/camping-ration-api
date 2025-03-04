package com.kanstantin.camping.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private Float calories;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private Set<Integer> categories = new HashSet<>();

}
