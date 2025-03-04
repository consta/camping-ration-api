package com.kanstantin.camping.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanstantin.camping.MyIntegrationTest;

public class GenericDaoTest extends MyIntegrationTest {
    @Autowired
    private GenericDao genericDao;

    @Test
    public void shouldCreateDish() throws Exception {
        DishM dish = new DishM();
        dish.setName("Spaghetti");
        dish.setDescription("Generic description");
        dish = genericDao.creatEntity(dish);
        assertTrue(dish.getId() > 0);
    }


    @Test
    public void shouldUpdateDish() throws Exception {
        // Create and persist a new dish
        DishM newDish = new DishM();
        newDish.setName("Spaghetti");
        newDish.setDescription("Generic description");
        final DishM dish = genericDao.creatEntity(newDish);

        // Update the dish details
        dish.setName("Fettuccine");
        dish.setDescription("Updated description");
        genericDao.updateEntity(dish);

        // Retrieve all dishes and check for the updated one
        List<DishM> dishes = genericDao.readAll(DishM.class);
        boolean isUpdated = dishes.stream()
                .anyMatch(d -> d.getId().equals(dish.getId())
                        && "Fettuccine".equals(d.getName())
                        && "Updated description".equals(d.getDescription()));
        assertTrue(isUpdated);
    }


    @Test
    public void shouldCreateCategory() throws Exception {
        CategoryM category = new CategoryM();
        category.setName("Desserts");
        category.setDescription("Sweet dishes");
        category = genericDao.creatEntity(category);
        assertTrue(category.getId() > 0);
    }

    @Test
    public void shouldUpdateCategory() throws Exception {
        // Create and persist a new category
        CategoryM newCategory = new CategoryM();
        newCategory.setName("Desserts");
        newCategory.setDescription("Sweet dishes");
        final CategoryM category = genericDao.creatEntity(newCategory);

        // Update the category details
        category.setName("Beverages");
        category.setDescription("Drinks and refreshments");
        genericDao.updateEntity(category);

        // Retrieve all categories and check for the updated one
        List<CategoryM> categories = genericDao.readAll(CategoryM.class);
        boolean isUpdated = categories.stream()
                .anyMatch(c -> c.getId().equals(category.getId())
                        && "Beverages".equals(c.getName())
                        && "Drinks and refreshments".equals(c.getDescription()));
        assertTrue(isUpdated);
    }

    @Test
    public void shouldCreateIngredient() throws Exception {
        ProductM product = new ProductM();
        product.setName("Tomato");
        product.setDescription("Fresh tomato");
        product.setCalories(125.0);
        product.setFats(5.0);
        product.setCarbohydrates(25.0);
        product.setProteins(12.5);
        product = genericDao.creatEntity(product);

        IngredientM ingredient = new IngredientM();
        ingredient.setComment("Chopped tomato");
        ingredient.setAmount(200.0);
        ingredient.setProductId(10000);
        ingredient.setDishId(1);
        ingredient = genericDao.creatEntity(ingredient);
        assertTrue(ingredient.getId() > 0);
    }

    @Test
    public void shouldUpdateIngredient() throws Exception {
        // Create and persist a new ingredient
        ProductM product = new ProductM();
        product.setName("Salsa");
        product.setDescription("Fresh salsa");
        product.setCalories(125.0);
        product.setFats(5.0);
        product.setCarbohydrates(25.0);
        product.setProteins(12.5);
        product = genericDao.creatEntity(product);

        IngredientM newIngredient = new IngredientM();
        newIngredient.setComment("Chopped tomato");
        newIngredient.setAmount(200.0);
        newIngredient.setProductId(10001);
        newIngredient.setDishId(1);
        final IngredientM ingredient = genericDao.creatEntity(newIngredient);

        // Update the ingredient details
        ingredient.setComment("Diced tomato");
        ingredient.setAmount(150.0);
        genericDao.updateEntity(ingredient);

        // Retrieve all ingredients and check for the updated one
        List<IngredientM> ingredients = genericDao.readAll(IngredientM.class);
        boolean isUpdated = ingredients.stream()
                .anyMatch(i -> i.getId().equals(ingredient.getId())
                        && "Diced tomato".equals(i.getComment())
                        && 150.0 == i.getAmount());
        assertTrue(isUpdated);
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        ProductM product = new ProductM();
        product.setName("Chedder");
        product.setDescription("Chedder cheese");
        product.setCalories(125.0);
        product.setFats(5.0);
        product.setCarbohydrates(25.0);
        product.setProteins(12.5);
        product = genericDao.creatEntity(product);
        assertTrue(product.getId() > 0);
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        // Create and persist a new product
        ProductM newProduct = new ProductM();
        newProduct.setName("Gordon Blue");
        newProduct.setDescription("Gordon Blue cheese");
        newProduct.setCalories(125.0);
        newProduct.setFats(5.0);
        newProduct.setCarbohydrates(25.0);
        newProduct.setProteins(12.5);
        final ProductM product = genericDao.creatEntity(newProduct);

        // Update the product details
        product.setName("Gordon Blue modified");
        product.setDescription("Aged cheddar cheese");
        genericDao.updateEntity(product);

        // Retrieve all products and check for the updated one
        List<ProductM> products = genericDao.readAll(ProductM.class);
        boolean isUpdated = products.stream()
                .anyMatch(p -> p.getId().equals(product.getId())
                        && "Gordon Blue modified".equals(p.getName())
                        && "Aged cheddar cheese".equals(p.getDescription()));
        assertTrue(isUpdated);
    }
    
}
