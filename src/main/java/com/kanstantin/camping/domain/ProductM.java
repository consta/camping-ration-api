package com.kanstantin.camping.domain;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@TableM(name = "product")
public class ProductM extends EntityM {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_CREATE = "INSERT INTO product (name, description, calories, proteins, fats, carbohydrates) VALUES (?, ?, ?, ?, ?, ?)";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_UPDATE = "UPDATE product SET name = ?, description = ?, calories = ?, proteins = ?, fats = ?, carbohydrates = ? WHERE id = ?";


    private String name;
    private String description;
    private Double calories;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Set<Integer> categoryIds = new HashSet<>();

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return connection -> {
            var statement = connection.prepareStatement(
                    SQL_CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDouble(3, calories);
            statement.setDouble(4, proteins);
            statement.setDouble(5, fats);
            statement.setDouble(6, carbohydrates);
            return statement;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return connection -> {
            var statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDouble(3, calories);
            statement.setDouble(4, proteins);
            statement.setDouble(5, fats);
            statement.setDouble(6, carbohydrates);
            statement.setInt(7, id);
            return statement;
        };
    }
}
