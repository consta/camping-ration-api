package com.kanstantin.camping.domain;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Data
public class DishCategoryEntity {
    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String parentDishCat;
}

