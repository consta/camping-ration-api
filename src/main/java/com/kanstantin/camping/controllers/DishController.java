package com.kanstantin.camping.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanstantin.camping.model.DishDTO;
import com.kanstantin.camping.model.MyResponse;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.services.DishService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class DishController {
    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getDishes() {
        MyResponse response = new MyResponse(1000, "success", dishService.getDishes());
        log.info("retrieved all dishes, {}", response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes", method = RequestMethod.POST)
    public ResponseEntity<MyResponse> addProduct(@RequestBody DishDTO dto) {
        MyResponse response = new MyResponse(1000, "success", dishService.createDish(dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/dishes/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getDishById(@PathVariable(name = "id") Integer id) {
        MyResponse response = new MyResponse(1000, "success", dishService.getDish(id));
        return ResponseEntity.ok(response);
    }
}
