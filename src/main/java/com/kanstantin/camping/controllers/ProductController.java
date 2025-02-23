package com.kanstantin.camping.controllers;

import com.kanstantin.camping.model.MyResponse;
import com.kanstantin.camping.model.CategoryDTO;
import com.kanstantin.camping.model.ProductDTO;
import com.kanstantin.camping.services.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductsService productsService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getProducts() {
        MyResponse response = new MyResponse(1000, "success", productsService.getProducts());
        log.info("retrieved all products, {}", response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<MyResponse> addProduct(@RequestBody ProductDTO dto) {
        MyResponse response = new MyResponse(1000, "success", productsService.createProduct(dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getProductById(@PathVariable(name = "id") Integer id) {

        ProductDTO dto = productsService.getProduct(id);
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
        ProductDTO productBefore = productsService.getProduct(Integer.parseInt(id));
        if (productBefore == null) {
            return ResponseEntity.notFound().build();
        }
        ProductDTO productAfter = productsService.updateProduct(newProductDTO);
        return ResponseEntity.ok(new MyResponse(1000, "success", productAfter));
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MyResponse> deleteProduct(@PathVariable(name = "id") String id) {
        ProductDTO product = productsService.getProduct(Integer.parseInt(id));
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productsService.deleteProduct(product.getId());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getCategories() {
        List<CategoryDTO> categories = productsService.getCategories();
        return ResponseEntity.ok(new MyResponse(1000, "success", categories));
    }
}
