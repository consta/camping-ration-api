package com.kanstantin.camping.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class IngredientsCustomRepositoryImpl implements IngredientsCustomRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Ingredient> findAllByDishId(Integer dishId) {
        TypedQuery<Ingredient> query = entityManager.createQuery("select di from Dish d join d.ingredients di where d.id = :dishId", Ingredient.class);
        query.setParameter("dishId", dishId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Ingredient addIngredientToDish(Integer dishId, Ingredient ingredient) {
        TypedQuery<Dish> query = entityManager.createQuery("select d from Dish d where d.id = :dishId", Dish.class);
        query.setParameter("dishId", dishId);
        Dish dish = query.getSingleResult();
        dish.getIngredients().add(ingredient);
        entityManager.persist(ingredient);
        entityManager.persist(dish);
        return ingredient;
    }

    @Override
    @Transactional
    public void deleteIngredientFromDish(Integer dishId, Integer ingredientId) {
        TypedQuery<Dish> query = entityManager.createQuery("select d from Dish d where d.id = :dishId", Dish.class);
        query.setParameter("dishId", dishId);
        Dish dish = query.getSingleResult();
        Ingredient ingredient = entityManager.find(Ingredient.class, ingredientId);
        dish.getIngredients().remove(ingredient);
        entityManager.persist(dish);
    }
}
