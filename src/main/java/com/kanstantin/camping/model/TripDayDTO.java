package com.kanstantin.camping.model;


import java.util.List;

import lombok.Data;

@Data
public class TripDayDTO {
    private Integer id;
    private String name;
    private Integer participants;
    private String comment;
    private List<TripMealDTO> tripMeals;
}
