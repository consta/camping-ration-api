package com.kanstantin.camping.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {
    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private Float calories;

    @Column
    private Float proteins;

    @Column
    private Float fats;

    @Column
    private Float carbohydrates;

    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CategoryEntity> categories;



}
