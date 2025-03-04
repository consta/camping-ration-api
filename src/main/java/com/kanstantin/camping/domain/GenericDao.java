package com.kanstantin.camping.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;


@Component
public class GenericDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T extends EntityM> T creatEntity(T en) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(en.getCreateStatement(), keyHolder);
        Integer id = keyHolder.getKey().intValue();
        en.setId(id);
        return en;
    }
    
    public <T extends EntityM> boolean updateEntity(T en) {
        return jdbcTemplate.update(en.getUpdateStatement()) == 1;
    }

    public <T extends EntityM> List<T> readAll(Class<T> clazz) {
        String tableName = clazz.getAnnotation(TableM.class).name();
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz));
    }

    public <T extends EntityM> Optional<T> getById(Integer id, Class<T> clazz) {
        String tableName = clazz.getAnnotation(TableM.class).name();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz), id));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty(); // Return empty if no results are found
        }
    }

    public <T extends EntityM> boolean deleteById(Integer id, Class<T> clazz) {
        String tableName = clazz.getAnnotation(TableM.class).name();
        String sql = "DELETE * FROM " + tableName + " WHERE id = " + id;
        return jdbcTemplate.update(sql) == 1;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
}
