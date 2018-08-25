package com.kanstantin.camping.controllers;

import com.kanstantin.camping.domain.MyResponse;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/products")
    @GetMapping
    public MyResponse getProducts() {
        return new MyResponse(1000, "success", productService.getProducts());
    }
}