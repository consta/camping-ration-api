package com.kanstantin.camping;

import com.kanstantin.camping.domain.ProductEntity;
import com.kanstantin.camping.domain.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@Import(value = Application.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public class RepositoryTest {
    @Autowired
    private ProductRepository repository;

    @Test
    public void testProductsAccessable() throws Exception {
        assertTrue(repository.findAll() != null);
    }

    @Test
    public void testProductRead() throws Exception {
        ProductEntity p = repository.findById(10000).get();
        assertNotNull(p.getName());
        assertNotNull(p.getCalories());
        assertNotNull(p.getProteins());
        assertNotNull(p.getFats());
        assertNotNull(p.getCarbohydrates());
        assertNotNull(p.getCategories());
        assertTrue(p.getCategories().size() == 2);
        log.info("Product = {}", p);
    }
}
