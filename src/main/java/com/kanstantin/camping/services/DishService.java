package com.kanstantin.camping.services;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kanstantin.camping.domain.Dish;
import com.kanstantin.camping.domain.DishRepository;
import com.kanstantin.camping.domain.IngredientsRepository;
import com.kanstantin.camping.model.DishDTO;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private Mapper mapper; // Assuming a Mapper class exists for entity-DTO conversion

    /**
     * Retrieve all dishes.
     *
     * @return List of DishDTO
     */
    public List<DishDTO> getDishes() {
        List<DishDTO> dishes = new ArrayList<>();
        dishRepository.findAll().forEach(e -> dishes.add(toDishDTO(e)));
        return dishes;
    }

    /**
     * Retrieve a dish by its ID.
     *
     * @param id Dish ID
     * @return DishDTO
     */
    public DishDTO getDish(Integer id) {
        return dishRepository.findById(id)
                .map(this::toDishDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found: " + id));
    }

    /**
     * Update an existing dish.
     *
     * @param updatedDishDto New data for the dish
     * @return Updated DishDTO
     */
    public DishDTO updateDish(DishDTO updatedDishDto) {
        Dish existingDish = dishRepository.findById(updatedDishDto.getId())
                .orElseThrow(() -> new RuntimeException("Dish with ID " + updatedDishDto.getId() + " not found!"));

        Dish updatedEntity = toDishEntity(updatedDishDto, existingDish);
        Dish savedEntity = dishRepository.save(updatedEntity);
        return toDishDTO(savedEntity);
    }

    /**
     * Create a new dish.
     *
     * @param dto Data for the new dish
     * @return Created DishDTO
     */
    public DishDTO createDish(DishDTO dto) {
        Dish entity = new Dish();
        mapper.map(dto, entity);
        entity = dishRepository.save(entity);
        return toDishDTO(entity);
    }

    /**
     * Delete a dish by its ID.
     *
     * @param id Dish ID
     */
    public void deleteDish(Integer id) {
        if (!dishRepository.existsById(id)) {
            throw new RuntimeException("Dish with ID " + id + " not found!");
        }
        dishRepository.deleteById(id);
    }

    // --- Private Helper Methods for Mappings ---

    /**
     * Convert DishEntity to DishDTO.
     *
     * @param dish The entity to map
     * @return Mapped DishDTO
     */
    private DishDTO toDishDTO(Dish dish) {
        return mapper.map(dish, DishDTO.class);
    }

    /**
     * Convert DishDTO to DishEntity.
     * Preserve existing entity state during update.
     *
     * @param dto     The DTO to map
     * @param entity  The entity to update (or new entity for creation)
     * @return Mapped DishEntity
     */
    private Dish toDishEntity(DishDTO dto, Dish entity) {
//        Set<DishIngredientsEntity> dishIngredientsEntities = new HashSet<>();
//        dto.getIngredients()
//                .forEach(dishIngredientDTO ->
//                        {
//                            IngredientsEntity newDishIngredient = new IngredientsEntity();
//                            newDishIngredient.setProductId(dishIngredientDTO.getProductId());
//                            newDishIngredient.setComment(dishIngredientDTO.getComment());
//                            newDishIngredient.setAmount(dishIngredientDTO.getAmount());
//                            DishIngredientsEntity dishIngredientsEntity = new DishIngredientsEntity();
//                            dishIngredientsEntity.setIngredient(newDishIngredient);
//                            dishIngredientsEntity.setDish(entity);
//                            dishIngredientsEntities.add(dishIngredientsEntity);
//                        });
//        entity.setDishIngredients(dishIngredientsEntities);
        return entity;
    }

}
