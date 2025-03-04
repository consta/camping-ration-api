package com.kanstantin.camping.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.model.IngredientDTO;
import com.kanstantin.camping.model.ProductDTO;

@Component
public class DishDao {
    private final GenericDao genericDao;
    private final ProductDao productDao;
    private final Mapper mapper;

    public DishDao(GenericDao genericDao, ProductDao productDao, Mapper mapper) {
        this.genericDao = genericDao;
        this.productDao = productDao;
        this.mapper = mapper;
    }

    public List<DishDTO> readAll() {
        List<DishDTO> dishes = genericDao.readAll(DishM.class).stream().map(m -> mapper.map(m, DishDTO.class)).collect(Collectors.toList());

        for (DishDTO dish : dishes) {
            dish.getIngredients().addAll(readIngredients(dish.getId()));
        }

        return dishes;
    }

    private Set<IngredientDTO> readIngredients(Integer dishId) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sql = "select * from ingredient where dish_id = " + dishId;
        List<IngredientM> entities = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(IngredientM.class));
        return entities.stream()
                .map(m -> {
                    IngredientDTO dto = mapper.map(m, IngredientDTO.class);
                    ProductDTO product = productDao.readProductById(m.getProductId());
                    dto.setProduct(product);
                    return dto;
                }).collect(Collectors.toSet());
    }

    public DishDTO readById(Integer id) {
        DishDTO dishDTO = genericDao.getById(id, DishM.class).map(m -> mapper.map(m, DishDTO.class)).orElse(null);
        if (dishDTO != null) {
            dishDTO.getIngredients().addAll(readIngredients(dishDTO.getId()));
        }
        return dishDTO;
    }

    public DishDTO save(DishDTO dish) {
        DishM dishM = mapper.map(dish, DishM.class);
        if (dish.getId() != null) {
            genericDao.updateEntity(dishM);
            return dish;
        }
        else {
            DishM updatedM = genericDao.creatEntity(dishM);
            return mapper.map(updatedM, DishDTO.class);
        }
    }

    public boolean delete(Integer id) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sql = "delete from ingredient where dish_id = " + id;
        jdbcTemplate.update(sql);
        return genericDao.deleteById(id, DishM.class);
    }

    public IngredientDTO addIngredient(Integer dishId, IngredientDTO ingredient) {
        IngredientM ingredientM = mapper.map(ingredient, IngredientM.class);
        ingredientM.setDishId(dishId);
        return Optional.ofNullable(genericDao.creatEntity(ingredientM)).map(m -> mapper.map(m, IngredientDTO.class)).orElse(null);
    }

    public boolean deleteIngredient(Integer ingredientId) {
        return genericDao.deleteById(ingredientId, IngredientM.class);
    }
}
