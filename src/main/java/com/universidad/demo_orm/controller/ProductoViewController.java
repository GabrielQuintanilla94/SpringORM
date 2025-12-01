package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Producto;
import com.universidad.demo_orm.repository.CategoriaRepository;
import com.universidad.demo_orm.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/productos") // Esta es la "puerta" principal
public class ProductoViewController {

    @Autowired // IMPORTANTE: Esto conecta la base de datos
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository; // 1. Inyectamos el repositorio de categorías

    // 1. LISTAR PRODUCTOS (La página que te da error 500)
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "productos-lista"; // templates/productos-lista.html
    }

    // 2. MOSTRAR FORMULARIO DE GUARDAR
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form"; // templates/producto-form.html
    }

    // GUARDAR (CREAR / EDITAR)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/vista/productos"; // Nos regresa a la lista
    }

    // 4. MOSTRAR FORMULARIO DE EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
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