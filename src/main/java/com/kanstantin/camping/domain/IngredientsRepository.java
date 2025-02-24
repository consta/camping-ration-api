package com.kanstantin.camping.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsRepository extends IngredientsCustomRepository, CrudRepository<Ingredient, Integer> {
}
