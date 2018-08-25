package com.kanstantin.camping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResponse {
    private Integer code;
    private String message;
    private Object data;

}
