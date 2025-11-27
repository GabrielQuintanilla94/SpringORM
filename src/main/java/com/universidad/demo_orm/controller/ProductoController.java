package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Producto;
import com.universidad.demo_orm.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController 
@RequestMapping("/productos") 
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> traerTodos() {
        return productoRepository.findAll(); 
    }
}