package com.kanstantin.camping.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dozer.Mapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.model.IngredientDTO;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.model.TripDTO;
import com.kanstantin.camping.model.TripDayDTO;

@Component
public class TripDao {
    private final GenericDao genericDao;
    private final TripDayDao tripDayDao;
    private final Mapper mapper;

    public TripDao(GenericDao genericDao, TripDayDao tripDayDao, Mapper mapper) {
        this.genericDao = genericDao;
        this.tripDayDao = tripDayDao;
        this.mapper = mapper;
    }

    public List<TripDTO> readAll() {
        return readAll(false);
    }

    public List<TripDTO> readAll(Boolean readDescendats) {
        List<TripDTO> trips = genericDao.readAll(TripM.class).stream().map(m -> mapper.map(m, TripDTO.class)).collect(Collectors.toList());
        if (BooleanUtils.isTrue(readDescendats)) {
            trips.forEach(trip -> {
                List<TripDayDTO> tripDayDTOS = tripDayDao.readTripDays(trip.getId(), readDescendats);
                trip.setTripDays(tripDayDTOS);
            });
        }
        return trips;
    }

    public TripDTO readById(Integer id) {
        TripDTO tripDTO = genericDao.getById(id, TripM.class).map(m -> mapper.map(m, TripDTO.class)).orElse(null);

        return tripDTO;
    }

    public TripDTO save(TripDTO trip) {
        TripM tripM = mapper.map(trip, TripM.class);
        if (tripM.getId() != null) {
            genericDao.updateEntity(tripM);
            return trip;
        }
        else {
            TripM updatedM = genericDao.creatEntity(tripM);
            return mapper.map(updatedM, TripDTO.class);
        }
    }

    public boolean delete(Integer id) {
        return genericDao.deleteById(id, TripM.class);
    }
}
