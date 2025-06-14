package com.kanstantin.camping.domain;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.Data;

@Data
@TableM(name = "tripday")
public class TripDayM extends EntityM {
    private static final String SQL_INSERT_TRIP = "insert into tripday (name, participants, comment, trip_id) values (?, ?, ?, ?)";
    private static final String SQL_UPDATE_TRIP = "update tripday set name = ?, participants = ?, comment = ?, trip_id = ? where id = ?";

    private String name;
    private Integer participants;
    private String comment;
    private Integer tripId;

    @Override
    public PreparedStatementCreator getCreateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_TRIP, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, participants);
            ps.setString(3, comment);
            ps.setInt(4, tripId);

            return ps;
        };
    }

    @Override
    public PreparedStatementCreator getUpdateStatement() {
        return conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_TRIP);
            ps.setString(1, name);
            ps.setInt(2, participants);
            ps.setString(3, comment);
            ps.setInt(4, tripId);
            ps.setInt(5, id);
            return ps;
        };
    }
}
