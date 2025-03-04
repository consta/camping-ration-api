package com.kanstantin.camping.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanstantin.camping.MyIntegrationTest;
import com.kanstantin.camping.model.ProductDTO;

public class ProductApiControllerTest extends MyIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testProductsFindAll() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testProductsFindById() throws Exception {
        mockMvc.perform(get("/api/products/10000"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testProductsFindByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/products/99"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setId(10002);
        dto.setName("Updated name");
        dto.setCalories(999.9F);
        dto.setProteins(1.1F);
        dto.setFats(2.2F);
        dto.setCarbohydrates(3.3F);
        dto.setCategories(Set.of(202));
        ObjectMapper oMapper = new ObjectMapper();
        String json = oMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/products/" + dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setName("Brand new product");
        dto.setCalories(99.99F);
        dto.setProteins(11.11F);
        dto.setFats(22.22F);
        dto.setCarbohydrates(33.33F);
        dto.setCategories(Set.of(201));
        ObjectMapper oMapper = new ObjectMapper();
        String json = oMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCategoriesFindAll() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
