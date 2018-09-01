package com.kanstantin.camping.domain;

import lombok.Data;
import org.dozer.Mapping;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Constraint;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // exclude from mapping with dozer
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CategoryEntity> categories;



}
