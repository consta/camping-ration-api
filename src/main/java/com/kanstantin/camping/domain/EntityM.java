package com.kanstantin.camping.domain;

import java.io.Serializable;

import org.springframework.jdbc.core.PreparedStatementCreator;

import lombok.Data;

@Data
public abstract class EntityM implements Serializable {
    Integer id;

    public abstract PreparedStatementCreator getCreateStatement();
    public abstract PreparedStatementCreator getUpdateStatement();

}
