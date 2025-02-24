package com.kanstantin.camping.domain;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsCustomRepository {
    List<Ingredient> findAllByDishId(Integer dishId);
    Ingredient addIngredientToDish(Integer dishId, Ingredient ingredient);
    void deleteIngredientFromDish(Integer dishId, Integer ingredientId);
}
