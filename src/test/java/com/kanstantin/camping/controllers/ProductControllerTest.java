package com.kanstantin.camping.controllers;

import com.kanstantin.camping.MyIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration("WebContent")
public class ProductControllerTest extends MyIntegrationTest {
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
}