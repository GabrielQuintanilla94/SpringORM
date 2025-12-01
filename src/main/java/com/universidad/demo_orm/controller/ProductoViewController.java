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
    private CategoriaRepository categoriaRepository;

    // 1. LISTAR PRODUCTOS
    @GetMapping
    public String listarProductos(Model model) {
        var lista = productoRepository.findAll();
        model.addAttribute("listaProductos", lista);
        return "productos-lista";
    }

    // 2. MOSTRAR FORMULARIO DE CREAR
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        // Enviamos la lista de categorías para el <select>
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "producto-form";
    }

    // 3. GUARDAR (Recibe los datos del form)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/vista/productos";
    }

    // 4. MOSTRAR FORMULARIO DE EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) { // <-- CAMBIO AQUÍ: Integer
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        model.addAttribute("producto", producto);
        // Enviamos la lista de categorías también al editar
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        
        return "producto-form";
    }

    // 5. ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) { // <-- CAMBIO AQUÍ: Integer
        productoRepository.deleteById(id);
        return "redirect:/vista/productos";
    }
}