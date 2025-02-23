package com.kanstantin.camping.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dish")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Ingredient.class, orphanRemoval = true)
    @JoinTable(
            name = "dish_ingredients", // Name of the intermediate table
            joinColumns = @JoinColumn(name = "dish_id"), // The owning side column (referencing the parent table)
            inverseJoinColumns = @JoinColumn(name = "ingredient_id") // The column referencing the `Ingredient` table
    )
    private Set<Ingredient> ingredients = new HashSet<>();

}
