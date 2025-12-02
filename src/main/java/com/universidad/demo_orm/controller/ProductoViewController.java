package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Producto;
import com.universidad.demo_orm.repository.CategoriaDao;
import com.universidad.demo_orm.repository.ProductoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vista/productos")
public class ProductoViewController {

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private CategoriaDao categoriaDao;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("listaProductos", productoDao.listarTodos());
        return "productos-lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaCategorias", categoriaDao.listarTodas());
        return "producto-form";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoDao.guardar(producto);
        return "redirect:/vista/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Producto producto = productoDao.buscarPorId(id);
        if (producto == null) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }

        model.addAttribute("producto", producto);
        model.addAttribute("listaCategorias", categoriaDao.listarTodas());
        return "producto-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoDao.eliminar(id);
        return "redirect:/vista/productos";
    }
}