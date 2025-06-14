package com.kanstantin.camping.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dozer.Mapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.model.TripDTO;
import com.kanstantin.camping.model.TripDayDTO;
import com.kanstantin.camping.model.TripMealDTO;

@Component
public class TripMealDao {
    private final GenericDao genericDao;
    private final Mapper mapper;

    public TripMealDao(GenericDao genericDao, Mapper mapper) {
        this.genericDao = genericDao;
        this.mapper = mapper;
    }

    public List<TripMealDTO> readTripMeals(Integer tripDayId, Boolean readDescendants) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlReadTripMealIds = "select id from tripmeal where tripday_id = " + tripDayId;
        List<Integer> tripMealIds = jdbcTemplate.query(sqlReadTripMealIds, (resultSet, i) -> resultSet.getInt("id"));
        String sqlReadTripMeals = "select * from tripmeal where id in (" + tripMealIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
        List<TripMealDTO> tripMealDtos  = jdbcTemplate.query(sqlReadTripMeals, BeanPropertyRowMapper.newInstance(TripMealDTO.class));
        if (BooleanUtils.isTrue(readDescendants)) {
            tripMealDtos.forEach(dto -> dto.setDishes(readDishesForMeal(dto.getId())));
        }
        return tripMealDtos;
    }


    public TripMealDTO readById(Integer id,  Boolean readDescendants) {
        TripMealDTO dto = genericDao.getById(id, TripMealM.class).map(m -> mapper.map(m, TripMealDTO.class)).orElse(null);
        if (BooleanUtils.isTrue(readDescendants) && dto != null) {
            dto.setDishes(readDishesForMeal(dto.getId()));
        }
        return dto;
    }

    private List<DishDTO> readDishesForMeal(Integer tripmeal_id) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlReadDishIds = "select dish_id from tripmeal_dishes where tripmeal_id = " + tripmeal_id;
        List<Integer> dishIds = jdbcTemplate.query(sqlReadDishIds, (resultSet, i) -> resultSet.getInt("dish_id"));
        if (dishIds.isEmpty()) {
            return new ArrayList<>();
        }
        String sqlReadDishes = "select * from dish where id in (" + dishIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
        List<DishDTO> dishDTOs = jdbcTemplate.query(sqlReadDishes, BeanPropertyRowMapper.newInstance(DishDTO.class));
        return dishDTOs;
    }

    public TripMealDTO save(Integer dayId, TripMealDTO trip) {
        TripMealM entityM = mapper.map(trip, TripMealM.class);
        entityM.setTripDayId(dayId);
        if (entityM.getId() != null) {
            genericDao.updateEntity(entityM);
            return trip;
        }
        else {
            TripMealM updatedM = genericDao.creatEntity(entityM);
            return mapper.map(updatedM, TripMealDTO.class);
        }
    }

    public boolean delete(Integer id) {
        // delete referenced tripmeal dishes
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlReadIds = "select id from tripmeal_dishes where tripmeal_id = " + id;
        List<Integer> tripMealDishIDs = jdbcTemplate.query(sqlReadIds, (resultSet, i) -> resultSet.getInt("id"));
        String sqlDeleteTripmealDishes = "delete from tripmeal_dishes where id in (" + tripMealDishIDs.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
        jdbcTemplate.update(sqlDeleteTripmealDishes);

        // delete tripmeal
        return genericDao.deleteById(id, TripMealM.class);
    }

}
