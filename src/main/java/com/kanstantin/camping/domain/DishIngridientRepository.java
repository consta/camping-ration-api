package com.kanstantin.camping.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishIngridientRepository extends CrudRepository<DishIngridientsEntity, Integer> {
}
