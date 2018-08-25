package com.kanstantin.camping.controllers;

import com.kanstantin.camping.domain.MyResponse;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getProducts() {
        MyResponse response = new MyResponse(1000, "success", productService.getProducts());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getProducts(@PathVariable(name = "id") String id) {

        ProductDTO dto = productService.getProduct(Integer.parseInt(id));
        ResponseEntity<MyResponse> responseEntity = null;

        if (dto != null) {
            responseEntity = ResponseEntity.ok(new MyResponse(1000, "success", dto));
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }
}