package com.kanstantin.camping.domain;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@TableM(name = "ingredient")
public class IngredientM extends EntityM {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_CREATE = "INSERT INTO ingredient (comment, amount, product_id, dish_id) VALUES (?, ?, ?, ?)";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_UPDATE = "UPDATE ingredient SET comment = ?, amount = ?, product_id = ?, dish_id = ? WHERE id = ?";

    private String comment;
    private Double amount;
    private Integer productId;
    private Integer dishId;

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return connection -> {
            var statement = connection.prepareStatement(
                    SQL_CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, comment);
            statement.setDouble(2, amount);
            statement.setInt(3, productId);
            statement.setInt(4, dishId);
            return statement;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return connection -> {
            var statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, comment);
            statement.setDouble(2, amount);
            statement.setInt(3, productId);
            statement.setInt(4, dishId);
            statement.setInt(5, id);
            return statement;
        };
    }
}
