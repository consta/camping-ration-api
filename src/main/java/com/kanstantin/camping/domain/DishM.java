package com.kanstantin.camping.domain;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@TableM(name = "dish")
public class DishM extends EntityM {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_INSERT_DISH = "insert into dish (name, description) values (?, ?)";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_UPDATE_DISH = "update dish set name = ?, description = ? where id = ?";

    private String name;
    private String description;
    private Set<IngredientM> ingredients = new HashSet<>();

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_DISH, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            return ps;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_DISH);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);
            return ps;
        };
    }
}
