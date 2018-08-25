package com.kanstantin.camping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Greeting {

    @Getter
    private final long id;
    @Getter
    private final String content;

}