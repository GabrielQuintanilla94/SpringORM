package com.universidad.demo_orm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {
        // Carga la vista src/main/resources/templates/index.html
        return "index";
    }
}

