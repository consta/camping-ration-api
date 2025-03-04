package com.kanstantin.camping.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kanstantin.camping.domain.DishDao;
import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.model.IngredientDTO;
import com.kanstantin.camping.model.MyResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class DishApiController {
    @Autowired
    private DishDao dishService;

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getDishes() {
        MyResponse response = new MyResponse(1000, "success", dishService.readAll());
        log.info("retrieved all dishes, {}", response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes", method = RequestMethod.POST)
    public ResponseEntity<MyResponse> addDish(@RequestBody DishDTO dto) {
        MyResponse response = new MyResponse(1000, "success", dishService.save(dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getDishById(@PathVariable(name = "id") Integer id) {
        MyResponse response = new MyResponse(1000, "success", dishService.readById(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MyResponse> updateDish(@PathVariable(name = "id") Integer id, @RequestBody DishDTO updatedDishDto) {
        updatedDishDto.setId(id); // Setting the ID in the DTO
        MyResponse response = new MyResponse(1000, "success", dishService.save(updatedDishDto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes/{id}/ingredients", method = RequestMethod.POST)
    public ResponseEntity<MyResponse> addIngredient(@PathVariable(name = "id") Integer id, IngredientDTO dto) {
        MyResponse response = new MyResponse(1000, "success", dishService.addIngredient(id, dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes/{dishId}/ingredients/{ingredientId}", method = RequestMethod.POST)
    public ResponseEntity deleteIngredient(@PathVariable(name = "dishId") Integer dishId, @PathVariable(name = "ingredientId") Integer ingredientId) {
        dishService.deleteIngredient(ingredientId);
        MyResponse response = new MyResponse(1000, "success", "removed");
        return ResponseEntity.noContent().build();
    }

}
