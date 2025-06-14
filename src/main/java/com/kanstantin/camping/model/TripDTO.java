package com.kanstantin.camping.model;


import java.util.List;

import lombok.Data;

@Data
public class TripDTO {
    private Integer id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Integer participants;
    private String comment;
    private List<TripDayDTO> tripDays;
}
