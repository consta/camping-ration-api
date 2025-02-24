package com.kanstantin.camping.controllers;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.services.DishService;

@SpringBootTest
@AutoConfigureMockMvc
public class DishControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DishService dishService;

    @Test
    @Transactional
    public void getDishes_ShouldReturnCorrectResponse() throws Exception {
        // Arrange
        DishDTO dishDTO = new DishDTO();
        dishDTO.setName("Test Dish");
        dishDTO.setDescription("Test Dish Description");

        dishService.createDish(dishDTO);

        // Act
        ResultActions result = mvc.perform(get("/api/dishes")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(jsonPath("$.data.[*]", hasSize(1)))
                .andExpect(jsonPath("$.data.[0].name", is("Test Dish")))
                .andExpect(jsonPath("$.data.[0].description", is("Test Dish Description")));
    }

    @Test
    @Transactional
    public void getDishById_ShouldReturnCorrectResponse() throws Exception {
        // Arrange
        DishDTO dishDTO = new DishDTO();
        dishDTO.setName("Test Dish");
        dishDTO.setDescription("Test Dish Description");

        DishDTO createdDish = dishService.createDish(dishDTO);

        // Act
        ResultActions result = mvc.perform(get("/api/dishes/" + createdDish.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(jsonPath("$.data.name", is("Test Dish")))
                .andExpect(jsonPath("$.data.description", is("Test Dish Description")));
    }

    @Test
    @Transactional
    public void whenDishDoesNotExist_thenGetDishByIdShouldReturnNotFound() throws Exception {
        ResultActions resultWhenDishDoesNotExist = mvc.perform(get("/api/dishes/10000")
                .contentType(MediaType.APPLICATION_JSON));
        resultWhenDishDoesNotExist.andExpect(status().isNotFound());
    }
}
