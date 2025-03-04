package com.kanstantin.camping.controllers.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kanstantin.camping.domain.ProductDao;
import com.kanstantin.camping.model.ProductDTO;

@Controller
@RequestMapping("/products")
public class ProductsViewController {
    @Autowired
    private ProductDao productDao;

    @GetMapping("/view")
    public String view(Model model) {
        List<ProductDTO> dishes = productDao.readAllProducts();
        model.addAttribute("products", dishes);
        return "view-products";
    }
}
