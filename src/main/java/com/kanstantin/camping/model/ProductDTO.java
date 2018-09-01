package com.kanstantin.camping.model;

import com.kanstantin.camping.domain.CategoryEntity;
import lombok.Data;
import org.dozer.Mapping;

import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private Float calories;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private List<Integer> categories;

}
