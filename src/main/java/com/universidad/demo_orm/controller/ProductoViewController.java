package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Producto;
import com.universidad.demo_orm.repository.CategoriaRepository;
import com.universidad.demo_orm.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/productos")
public class ProductoViewController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository; // 1. Inyectamos el repositorio de categorías

    // LISTA DE PRODUCTOS
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "productos-lista";
    }

    // FORMULARIO NUEVO PRODUCTO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        // 2. Enviamos la lista de categorías a la vista
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "producto-form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/vista/productos";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        model.addAttribute("producto", producto);
        // 2. Enviamos la lista de categorías también al editar
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "producto-form";
    }

    // ELIMINAR 
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/vista/productos";
    }
}