package com.kanstantin.camping.model;


import java.util.List;

import lombok.Data;

@Data
public class TripMealDTO {
    private Integer id;
    private String name;
    private String comment;
    private List<DishDTO> dishes;
}
