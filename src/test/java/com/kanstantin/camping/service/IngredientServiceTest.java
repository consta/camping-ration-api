package com.kanstantin.camping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanstantin.camping.MyIntegrationTest;
import com.kanstantin.camping.model.IngredientDTO;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.services.IngredientService;

public class IngredientServiceTest extends MyIntegrationTest {
    @Autowired
    private IngredientService ingredientService;

    @Test
    public void findIngredientsByDishIdShouldReturnListOfIngredients() throws Exception {
        List<IngredientDTO> ingredients = this.ingredientService.findIngredientsByDishId(1);
        assertEquals(2, ingredients.size());
    }

    @Test
    public void addNewAndTheDeleteShouldPass() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(10000);
        IngredientDTO dto = new IngredientDTO();
        dto.setProduct(productDTO);
        dto.setComment("This is ingredient #3");
        dto.setAmount(300.0f);

        IngredientDTO newIngredient = ingredientService.addNewIngredient(1, dto);
        List<IngredientDTO> ingredients = this.ingredientService.findIngredientsByDishId(1);
        assertEquals(3, ingredients.size());
        assertTrue(newIngredient.getId() > 0);

        ingredientService.deleteIngredient(1, newIngredient.getId());
        ingredients = this.ingredientService.findIngredientsByDishId(1);
        assertEquals(2, ingredients.size());
    }



}
