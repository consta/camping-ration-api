package com.kanstantin.camping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResponse <T> {
    private Integer code;
    private String message;
    private T data;

}
