package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Inventario;
import com.universidad.demo_orm.entity.Movimiento;
import com.universidad.demo_orm.entity.Producto;
import com.universidad.demo_orm.repository.InventarioRepository;
import com.universidad.demo_orm.repository.MovimientoRepository;
import com.universidad.demo_orm.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired private MovimientoRepository movimientoRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private InventarioRepository inventarioRepo;

    // Listar movimientos
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listaMovimientos", movimientoRepo.findAll());
        return "movimientos/movimientos";
    }

    // Mostrar formulario de creación
    @GetMapping("/crear")
    public String createForm(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("productos", productoRepo.findAll());
        return "movimientos/create";
    }

    // Procesar Movimiento (Entrada/Salida)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Movimiento movimiento, Model model) {
        
        // Asignar datos automáticos
        movimiento.setId_usuario(1); // Usuario por defecto (temporal)
        movimiento.setFecha_movimiento(LocalDateTime.now());

        // Buscar el producto seleccionado
        Producto producto = productoRepo.findById(movimiento.getProducto().getId_producto())
                .orElse(null);

        if (producto == null) {
            return "redirect:/movimientos/crear";
        }

        // Buscar o crear registro de inventario para este producto
        Inventario inventario = inventarioRepo.findByProducto_IdProducto(producto.getId_producto())
                .orElse(new Inventario());

        if (inventario.getId_inventario() == null) {
            inventario.setProducto(producto);
            inventario.setStock_actual(0);
            inventario.setStock_minimo(10);
        }

        // Lógica de Negocio
        int cantidadFinal = movimiento.getCantidad();
        String tipoAccion = movimiento.getTipo_movimiento();

        if ("Entrada".equalsIgnoreCase(tipoAccion)) {
            // === LÓGICA DE COMPRA ===
            
            // Convertir cajas a unidades
            if (producto.getUnidades_por_caja() != null && producto.getUnidades_por_caja() > 0) {
                cantidadFinal = movimiento.getCantidad() * producto.getUnidades_por_caja();
            }

            // Calcular Costo Unitario Promedio y actualizar precio de compra
            if (cantidadFinal > 0 && movimiento.getCosto() != null && movimiento.getCosto().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal costoTotal = movimiento.getCosto();
                BigDecimal costoUnitario = costoTotal.divide(new BigDecimal(cantidadFinal), 2, RoundingMode.HALF_UP);

                producto.setPrecio_compra(costoUnitario);
                productoRepo.save(producto);
            }

            // Aumentar Stock
            inventario.setStock_actual(inventario.getStock_actual() + cantidadFinal);

            // Generar descripción
            movimiento.setDescripcion("COMPRA: " + movimiento.getCantidad() + " cajas (" + cantidadFinal + " un.) - Costo Unit: $" + producto.getPrecio_compra());

        } else if ("Salida".equalsIgnoreCase(tipoAccion)) {
            // === LÓGICA DE VENTA ===

            // Validar Stock suficiente
            if (inventario.getStock_actual() < cantidadFinal) {
                model.addAttribute("error", "⛔ STOCK INSUFICIENTE: Tienes " + inventario.getStock_actual() + ", intentas vender " + cantidadFinal);
                model.addAttribute("movimiento", movimiento);
                model.addAttribute("productos", productoRepo.findAll());
                return "movimientos/create";
            }

            // Disminuir Stock
            inventario.setStock_actual(inventario.getStock_actual() - cantidadFinal);

            // Calcular cobro automático (Precio Venta * Cantidad)
            BigDecimal cobro = producto.getPrecio_venta().multiply(new BigDecimal(cantidadFinal));
            movimiento.setCosto(cobro);
            
            movimiento.setDescripcion("VENTA: " + cantidadFinal + " unidades");
        }

        // Guardar cambios en BD
        inventario.setFecha_actualizacion(LocalDateTime.now());
        
        inventarioRepo.save(inventario);
        movimientoRepo.save(movimiento);

        return "redirect:/movimientos";
    }
}