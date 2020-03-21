package com.kanstantin.camping.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prod_category")
@Data
public class CategoryEntity {
    @Id
    private Integer id;

    @Column
    private String name;

    @Column(name = "parent_cat")
    private String parentCat;
}
