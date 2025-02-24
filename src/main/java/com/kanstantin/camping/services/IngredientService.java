package com.kanstantin.camping.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanstantin.camping.domain.Dish;
import com.kanstantin.camping.domain.DishRepository;
import com.kanstantin.camping.domain.Ingredient;
import com.kanstantin.camping.domain.IngredientsRepository;
import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.model.IngredientDTO;

@Service
public class IngredientService {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private Mapper mapper;

    public List<IngredientDTO> findIngredientsByDishId(Integer dishId) {
        return ingredientsRepository
                .findAllByDishId(dishId)
                .stream()
                .map(ingredient -> mapper.map(ingredient, IngredientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public IngredientDTO addNewIngredient(Integer dishId, IngredientDTO ingredientDTO) {
        Ingredient newIngredient = ingredientsRepository.addIngredientToDish(dishId, mapper.map(ingredientDTO, Ingredient.class));
        IngredientDTO dto = mapper.map(newIngredient, IngredientDTO.class);
        return dto;
    }

    public void deleteIngredient(Integer dishId, Integer ingredientId) {
        ingredientsRepository.deleteIngredientFromDish(dishId, ingredientId);
    }
}
