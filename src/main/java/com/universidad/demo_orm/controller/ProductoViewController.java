package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Producto;
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

    // 1. LISTAR PRODUCTOS (La página que te da error 500)
    @GetMapping
    public String listarProductos(Model model) {
        // Buscamos todos los productos en la BD
        var lista = productoRepository.findAll();
        
        // OJO AQUÍ: El nombre "listaProductos" debe ser IDÉNTICO al de tu HTML (línea 34)
        model.addAttribute("listaProductos", lista);
        
        // Retornamos el nombre del archivo HTML exacto (sin .html)
        return "productos-lista";
    }

    // 2. MOSTRAR FORMULARIO DE GUARDAR
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    // 3. GUARDAR (Recibe los datos del form)
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
        return "producto-form";
    }

    // 5. ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/vista/productos";
    }
}