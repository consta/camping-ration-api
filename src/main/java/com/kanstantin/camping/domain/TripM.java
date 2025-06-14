package com.kanstantin.camping.domain;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.Data;

@Data
@TableM(name = "trip")
public class TripM extends EntityM {
    private static final String SQL_INSERT_TRIP = "insert into trip (name, description, start_date, end_date, participants, comment) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TRIP = "update trip set name = ?, description = ?, start_date = ?, end_date = ?, participants = ?, comment =? where id = ?";

    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Integer participants;
    private String comment;

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_TRIP, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, startDate);
            ps.setString(4, endDate);
            ps.setInt(5, participants);
            ps.setString(6, comment);
            return ps;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_TRIP);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, startDate);
            ps.setString(4, endDate);
            ps.setInt(5, participants);
            ps.setString(6, comment);
            ps.setInt(7, id);
            return ps;
        };
    }
}
