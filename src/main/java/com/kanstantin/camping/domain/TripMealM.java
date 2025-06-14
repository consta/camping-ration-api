package com.kanstantin.camping.domain;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.Data;

@Data
@TableM(name = "tripmeal")
public class TripMealM extends EntityM {
    private static final String SQL_INSERT_TRIP = "insert into tripmeal (name, comment, tripday_id) values (?, ?, ?)";
    private static final String SQL_UPDATE_TRIP = "update tripmeal set name = ?, comment = ?, tripday_id = ? where id = ?";

    private String name;
    private String comment;
    private Integer tripDayId;

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_TRIP, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, comment);
            ps.setInt(3, tripDayId);

            return ps;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_TRIP);
            ps.setString(1, name);
            ps.setString(2, comment);
            ps.setInt(3, tripDayId);
            ps.setInt(4, id);
            return ps;
        };
    }
}
