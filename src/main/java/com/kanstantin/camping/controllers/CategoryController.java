package com.kanstantin.camping.controllers;

import com.kanstantin.camping.domain.MyResponse;
import com.kanstantin.camping.model.CategoryDTO;
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
public class CategoryController {
    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse> getCategories(@PathVariable(name = "id") String id) {

        CategoryDTO dto = commonService.getCategory(Integer.parseInt(id));
        ResponseEntity<MyResponse> responseEntity = null;

        if (dto != null) {
            responseEntity = ResponseEntity.ok(new MyResponse(1000, "success", dto));
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }
}
