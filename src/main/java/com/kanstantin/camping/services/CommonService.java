package com.kanstantin.camping.services;

import com.kanstantin.camping.domain.CategoryEntity;
import com.kanstantin.camping.domain.CategoryRepository;
import com.kanstantin.camping.domain.ProductEntity;
import com.kanstantin.camping.domain.ProductRepository;
import com.kanstantin.camping.model.ProductDTO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommonService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Mapper mapper;

    public List<ProductDTO> getProducts() {

        final List<ProductDTO> dtos = new ArrayList();
        productRepository.findAll().forEach(e -> dtos.add(toProductDTO(e)));
        return dtos;
    }

    public ProductDTO getProduct(Integer id) {
        return toProductDTO(productRepository.findById(id).orElse(null));
    }

    public ProductDTO updateProduct(ProductDTO newProductDto) {
        ProductEntity entity = toProductEntity(newProductDto);
        return toProductDTO(productRepository.save(entity));
    }

    public ProductDTO createProduct(ProductDTO dto) {

        ProductEntity entity = new ProductEntity();
        mapper.map(dto, entity);
        entity = productRepository.save(entity);
        entity = persistProductCategories(dto, entity);
        return toProductDTO(entity);
    }

    private ProductDTO toProductDTO(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        mapper.map(entity, dto);
        dto.setCategories(entity.getCategories().stream().map(CategoryEntity::getId).collect(Collectors.toList()));
        return dto;
    }

    private ProductEntity toProductEntity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        mapper.map(dto, entity);
        return persistProductCategories(dto, entity);
    }

    private ProductEntity persistProductCategories(ProductDTO dto, ProductEntity entity) {
        if (dto.getCategories() != null) {
            List<CategoryEntity> categoryEntities = new ArrayList<>();
            dto.getCategories()
                    .stream()
                    .forEach(i -> {
                        categoryEntities.add(categoryRepository
                                .findById(i)
                                .orElseThrow(() -> new IllegalArgumentException("Category with id = " + i + " doesn't exist")));
                    });
            entity.setCategories(new HashSet<>(categoryEntities));
            return productRepository.save(entity);
        }
        return entity;
    }
}
