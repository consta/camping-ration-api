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
@TableM(name = "category")
public class CategoryM extends EntityM {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_CREATE = "INSERT INTO category (name, description) VALUES (?, ?)";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SQL_UPDATE = "UPDATE category SET name = ?, description = ? WHERE id = ?";


    private String name;
    private String description;
    private Set<Integer> productIds = new HashSet<>();

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return connection -> {
            var statement = connection.prepareStatement(
                    SQL_CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, name);
            statement.setString(2, description);
            return statement;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return connection -> {
            var statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, id);
            return statement;
        };
}


}
