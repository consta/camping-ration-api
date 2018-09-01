package com.kanstantin.camping.controllers;

import com.kanstantin.camping.domain.MyResponse;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.services.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getProducts() {
        MyResponse response = new MyResponse(1000, "success", commonService.getProducts());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<MyResponse> addProduct(@RequestBody ProductDTO dto) {
        MyResponse response = new MyResponse(1000, "success", commonService.createProduct(dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getProducts(@PathVariable(name = "id") String id) {

        ProductDTO dto = commonService.getProduct(Integer.parseInt(id));
        ResponseEntity<MyResponse> responseEntity = null;

        if (dto != null) {
            responseEntity = ResponseEntity.ok(new MyResponse(1000, "success", dto));
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MyResponse> updateProduct(@PathVariable(name = "id") String id,
                                                    @RequestBody ProductDTO newProductDTO) {
        ProductDTO productBefore = commonService.getProduct(Integer.parseInt(id));
        if (productBefore == null) {
            return ResponseEntity.notFound().build();
        }
        ProductDTO productAfter = commonService.updateProduct(newProductDTO);
        return ResponseEntity.ok(new MyResponse(1000, "success", productAfter));
    }

}