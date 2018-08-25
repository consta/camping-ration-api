package com.kanstantin.camping.services;

import com.kanstantin.camping.domain.ProductEntity;
import com.kanstantin.camping.domain.ProductRepository;
import com.kanstantin.camping.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<ProductDTO> getProducts() {

        final List<ProductDTO> dtos = new ArrayList();
        repository.findAll().forEach(e -> {
            dtos.add(toProductDTO(e));
        });
        return dtos;
    }

    private ProductDTO toProductDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

}
