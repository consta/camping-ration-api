package com.kanstantin.camping.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dozer.Mapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kanstantin.camping.model.TripDayDTO;
import com.kanstantin.camping.model.TripMealDTO;

@Component
public class TripDayDao {
    private final GenericDao genericDao;
    private final TripMealDao tripMealDao;
    private final Mapper mapper;

    public TripDayDao(GenericDao genericDao, TripMealDao tripMealDao, Mapper mapper) {
        this.genericDao = genericDao;
        this.tripMealDao = tripMealDao;
        this.mapper = mapper;
    }

    public List<TripDayDTO> readTripDays(Integer tripId, Boolean readDescendants) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlReadTripDayIds = "select id from tripday where trip_id = " + tripId;
        List<Integer> tripDayIds = jdbcTemplate.query(sqlReadTripDayIds, (resultSet, i) -> resultSet.getInt("id"));
        if (tripDayIds.isEmpty()) {
            return new ArrayList<>();
        }
        String sqlReadTripDays = "select * from tripday where id in (" + tripDayIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
        List<TripDayDTO> tripDayDtos  = jdbcTemplate.query(sqlReadTripDays, BeanPropertyRowMapper.newInstance(TripDayDTO.class));
        if (BooleanUtils.isTrue(readDescendants)) {
            tripDayDtos.forEach(tripDayDto -> {
                List<TripMealDTO> tripMealDTOS = tripMealDao.readTripMeals(tripDayDto.getId(), readDescendants);
                tripDayDto.setTripMeals(tripMealDTOS);
            });
        }
        return tripDayDtos;
    }

    public TripDayDTO readById(Integer id, Boolean readDescendants) {
        TripDayDTO tripDayDTO = genericDao.getById(id, TripDayM.class).map(m -> mapper.map(m, TripDayDTO.class)).orElse(null);
        if (BooleanUtils.isTrue(readDescendants) && tripDayDTO != null) {
            tripDayDTO.setTripMeals(tripMealDao.readTripMeals(tripDayDTO.getId(), readDescendants));
        }
        return tripDayDTO;
    }

    public TripDayDTO save(Integer tripId, TripDayDTO dto) {
        TripDayM tripDayM = mapper.map(dto, TripDayM.class);
        tripDayM.setTripId(tripId);
        if (tripDayM.getId() != null) {
            genericDao.updateEntity(tripDayM);
            return dto;
        }
        else {
            TripDayM updatedM = genericDao.creatEntity(tripDayM);
            return mapper.map(updatedM, TripDayDTO.class);
        }
    }

    public boolean delete(Integer id) {
        return genericDao.deleteById(id, TripM.class);
    }
}
