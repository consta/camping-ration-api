package com.kanstantin.camping.controllers;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanstantin.camping.MyIntegrationTest;
import com.kanstantin.camping.domain.DishDao;
import com.kanstantin.camping.model.DishDTO;

public class DishApiControllerTest extends MyIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getDishesTest() throws Exception {
        DishDTO dish = new DishDTO();
        dish.setName("Test Dish");

        dish = dishDao.save(dish);

        this.mockMvc.perform(get("/api/dishes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].name", hasItem(dish.getName())))
                .andExpect(jsonPath("$.data[*].id", hasItem(dish.getId())));
    }

    @Test
    public void addDishTest() throws Exception {
        DishDTO dish = new DishDTO();
        dish.setName("Test Dish");

        this.mockMvc.perform(post("/api/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dish)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.name", is(dish.getName())));
    }
}
