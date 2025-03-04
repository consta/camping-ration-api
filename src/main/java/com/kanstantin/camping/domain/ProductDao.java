package com.kanstantin.camping.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kanstantin.camping.model.CategoryDTO;
import com.kanstantin.camping.model.ProductDTO;

@Component
public class ProductDao {
    @Autowired
    private final GenericDao genericDao;

    @Autowired
    private final Mapper mapper;

    public ProductDao(GenericDao genericDao, Mapper mapper) {
        this.genericDao = genericDao;
        this.mapper = mapper;
    }

    public List<ProductDTO> readAllProducts() {
        return genericDao.readAll(ProductM.class).stream().map(m -> {
                 ProductDTO dto =   mapper.map(m, ProductDTO.class);
                 dto.setCategories(readCategoriesByProductId(m.getId()));
                 return dto;
                }
        ).collect(Collectors.toList());
    }

    public ProductDTO readProductById(Integer id) {
        return genericDao.getById(id, ProductM.class).map(m -> {
                    ProductDTO dto = mapper.map(m, ProductDTO.class);
                    dto.setCategories(readCategoriesByProductId(m.getId()));
                    return dto;
                }
        ).orElse(null);
    }

    private Set<Integer> readCategoriesByProductId(Integer productId) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sql = "select category_id from product_categories where product_id = " + productId;
        List<Integer> categories = jdbcTemplate.query(sql, (resultSet, i) -> resultSet.getInt("category_id"));
        return new HashSet<>(categories);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductM entity = mapper.map(productDTO, ProductM.class);
        if (productDTO.getId() != null) {
            genericDao.updateEntity(entity);
            updateCategories(productDTO.getId(), productDTO.getCategories());
            return productDTO;
        }
        else {
            entity = genericDao.creatEntity(entity);
            updateCategories(entity.getId(), productDTO.getCategories());
            return mapper.map(entity, ProductDTO.class);
        }
    }


    private void updateCategories(Integer productId, Set<Integer> categoryIds) {
        deleteProductCategoriesByProductId(productId);
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlInsertNew = "insert into product_categories (product_id, category_id) values (?, ?)";
        categoryIds.stream().forEach(categoryId -> jdbcTemplate.update(sqlInsertNew, productId, categoryId));
    }

    private void deleteProductCategoriesByProductId(Integer productId) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlDeleteOld = "delete from product_categories where product_id = ?";
        jdbcTemplate.update(sqlDeleteOld, productId);
    }

    public boolean deleteProductById(Integer id) {
        deleteProductCategoriesByProductId(id);
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlDeleteOld = "delete from product_categories where product_id = ?";
        jdbcTemplate.update(sqlDeleteOld, id);
        return genericDao.deleteById(id, ProductM.class);
    }

    public List<CategoryDTO> readAllCategories() {
        return genericDao.readAll(CategoryM.class).stream().map(m -> mapper.map(m, CategoryDTO.class)).collect(Collectors.toList());
    }

    public List<ProductDTO> readAllProductByCategory(Integer categoryId) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sql = "select p.* from product p join product_categories pc on p.id = pc.product_id where pc.category_id = " + categoryId;
        return jdbcTemplate
                .query(sql, BeanPropertyRowMapper.newInstance(ProductM.class))
                .stream().map(m -> mapper.map(m, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        CategoryM entity = mapper.map(categoryDTO, CategoryM.class);
        entity = genericDao.creatEntity(entity);
        categoryDTO.setId(entity.getId());
        return categoryDTO;
    }

    public boolean updateCategory(CategoryDTO categoryDTO) {
        CategoryM entity = mapper.map(categoryDTO, CategoryM.class);
        return genericDao.updateEntity(entity);
    }

    public boolean deleteCategory(Integer categoryId) {
        JdbcTemplate jdbcTemplate = genericDao.getJdbcTemplate();
        String sqlDeleteOld = "delete from product_categories where category_id = ?";
        jdbcTemplate.update(sqlDeleteOld, categoryId);

        return genericDao.deleteById(categoryId, CategoryM.class);
    }
}
