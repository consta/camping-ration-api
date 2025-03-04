package com.kanstantin.camping.controllers.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kanstantin.camping.domain.DishDao;
import com.kanstantin.camping.model.DishDTO;

@Controller
@RequestMapping("/dishes")
public class DishViewController {
    @Autowired
    private DishDao dishDao;

    @GetMapping("/view")
    public String view(Model model) {
        List<DishDTO> dishes = dishDao.readAll();
        model.addAttribute("dishes", dishes);
        return "view-dishes";
    }
}
