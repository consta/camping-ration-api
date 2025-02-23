package com.kanstantin.camping.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanstantin.camping.domain.Category;
import com.kanstantin.camping.domain.CategoryRepository;
import com.kanstantin.camping.domain.Product;
import com.kanstantin.camping.domain.ProductRepository;
import com.kanstantin.camping.model.CategoryDTO;
import com.kanstantin.camping.model.ProductDTO;

@Service
public class ProductsService {
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
        Product entity = toProductEntity(newProductDto);
        return toProductDTO(productRepository.save(entity));
    }

    public ProductDTO createProduct(ProductDTO dto) {

        Product entity = new Product();
        mapper.map(dto, entity);
        entity = productRepository.save(entity);
        entity = persistProductCategories(dto, entity);
        return toProductDTO(entity);
    }

    private ProductDTO toProductDTO(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        mapper.map(entity, dto);
        dto.setCategories(entity.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        return dto;
    }

    private Product toProductEntity(ProductDTO dto) {
        Product entity = new Product();
        mapper.map(dto, entity);
        return persistProductCategories(dto, entity);
    }

    private Product persistProductCategories(ProductDTO dto, Product entity) {
        if (dto.getCategories() != null) {
            List<Category> categoryEntities = new ArrayList<>();
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

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> result = new ArrayList<>();
        for (Category entity : categoryRepository.findAll()) {
            CategoryDTO dto = new CategoryDTO();
            mapper.map(entity, dto);
        }
        return result;
    }

    /**
     * Get all categories.
     *
     * @return List of CategoryDTO
     */
    public List<CategoryDTO> getAllCategories() {
        return getCategories();
    }

    /**
     * Add a new category.
     *
     * @param dto Category to add
     * @return Created CategoryDTO
     */
    public CategoryDTO addCategory(CategoryDTO dto) {
        Category entity = convertToEntity(dto);
        Category savedEntity = categoryRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    /**
     * Get category by ID.
     *
     * @param id Category ID
     * @return CategoryDTO
     */
    public CategoryDTO getCategoryById(Integer id) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return convertToDTO(entity);
    }

    /**
     * Update category by ID.
     *
     * @param id  Category ID
     * @param dto New data for category
     * @return Updated CategoryDTO
     */
    public CategoryDTO updateCategory(Integer id, CategoryDTO dto) {
        Category existingEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existingEntity.setName(dto.getName());

        Category updatedEntity = categoryRepository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    /**
     * Delete category by ID.
     *
     * @param id Category ID
     */
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    /**
     * Convert CategoryEntity to CategoryDTO.
     */
    private CategoryDTO convertToDTO(Category entity) {
        return new CategoryDTO(entity.getId(), entity.getName());
    }

    /**
     * Convert CategoryDTO to CategoryEntity.
     */
    private Category convertToEntity(CategoryDTO dto) {
        return new Category(dto.getId(), dto.getName());
    }
}
