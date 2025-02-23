package com.kanstantin.camping;

import com.kanstantin.camping.domain.Dish;
import com.kanstantin.camping.domain.Ingredient;
import com.kanstantin.camping.domain.Product;
import com.kanstantin.camping.domain.ProductRepository;
import com.kanstantin.camping.services.DishService;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Slf4j
public class RepositoryTest extends MyIntegrationTest {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private DishService dishService;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    EntityManager entityManager;

    @Test
    public void testProductsAccessable() throws Exception {
        assertTrue(repository.findAll() != null);
    }

    @Test
    public void testProductRead() throws Exception {
        Product p = repository.findById(10000).get();
        assertNotNull(p.getName());
        assertNotNull(p.getCalories());
        assertNotNull(p.getProteins());
        assertNotNull(p.getFats());
        assertNotNull(p.getCarbohydrates());
        assertNotNull(p.getCategories());
        assertTrue(p.getCategories().size() == 2);
        log.info("Product = {}", p);
    }

    @Test
    public void testSaveDish() throws Exception {
//        DishDTO dto = new DishDTO();
//        dto.setName("Dish 1");
//        dto.setDescription("Description");
//        List<IngredientDTO> ingredients = new ArrayList<>();
//        IngredientDTO i1 = new IngredientDTO();
//        i1.setProductId(10000);
//        i1.setAmount(100.0f);
//        i1.setComment("Comment 1");
//        IngredientDTO i2 = new IngredientDTO();
//        i2.setProductId(10001);
//        i2.setAmount(155.0f);
//        i2.setComment("Comment 2");
//        ingredients.add(i1);
//        ingredients.add(i2);
//        dto.setIngredients(ingredients);
//
//        dto = dishService.createDish(dto);
//        assertTrue(dto.getId() > 0);
//        DishDTO newDto = dishService.getDish(dto.getId());
//        System.out.println(newDto);
    }

    @Test
    public void testDishesFromHibernate() throws Exception {
        Session session = (entityManagerFactory.unwrap(SessionFactory.class)).openSession();
        session.beginTransaction();
        Product p1 = repository.findById(10000).get();
        Ingredient i1 = new Ingredient();
        i1.setProduct(p1);
        i1.setAmount(100.0f);
        i1.setComment("Comment 1");
        Product p2 = repository.findById(10001).get();
        Ingredient i2 = new Ingredient();
        i2.setProduct(p2);
        i2.setAmount(155.0f);
        i2.setComment("Comment 2");

        Dish d1 = new Dish();
        d1.setName("Dish 1");
        d1.setDescription("Description");
        d1.getIngredients().add(i1);
        d1.getIngredients().add(i2);

        session.save(d1);
        session.save(i1);
        session.save(i2);

        session.getTransaction().commit();
        assertTrue(true);

        Dish d2 = session.find(Dish.class, d1.getId());
        assertEquals(d2.getId(), d1.getId());
        assertEquals(d2.getIngredients().size(), 2);

    }
}
