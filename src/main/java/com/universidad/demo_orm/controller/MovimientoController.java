package com.universidad.demo_orm.controller;

import com.universidad.demo_orm.entity.Inventario;
import com.universidad.demo_orm.entity.Movimiento;
import com.universidad.demo_orm.entity.Producto;
import com.universidad.demo_orm.repository.InventarioDao;
import com.universidad.demo_orm.repository.MovimientoDao;
import com.universidad.demo_orm.repository.ProductoDao;
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

    @Autowired private MovimientoDao movimientoDao;
    @Autowired private ProductoDao productoDao;
    @Autowired private InventarioDao inventarioDao;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listaMovimientos", movimientoDao.listarTodos());
        return "movimientos/movimientos";
    }

    @GetMapping("/crear")
    public String createForm(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("productos", productoDao.listarTodos());
        return "movimientos/create";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Movimiento movimiento, Model model) {

        movimiento.setId_usuario(1);
        movimiento.setFecha_movimiento(LocalDateTime.now());

        if (movimiento.getProducto() == null || movimiento.getProducto().getId_producto() == null) {
            return "redirect:/movimientos/crear";
        }

        // Usamos el DAO manual
        Producto producto = productoDao.buscarPorId(movimiento.getProducto().getId_producto());

        if (producto == null) {
            return "redirect:/movimientos/crear";
        }

        // Usamos el DAO manual
        Inventario inventario = inventarioDao.buscarPorProductoId(producto.getId_producto());

        if (inventario == null) {
            inventario = new Inventario();
            inventario.setProducto(producto);
            inventario.setStock_actual(0);
            inventario.setStock_minimo(10);
        }

        int cantidadFinal = movimiento.getCantidad();
        String tipoAccion = movimiento.getTipo_movimiento();

        if ("Entrada".equalsIgnoreCase(tipoAccion)) {
            if (producto.getUnidades_por_caja() != null && producto.getUnidades_por_caja() > 0) {
                cantidadFinal = movimiento.getCantidad() * producto.getUnidades_por_caja();
            }

            if (cantidadFinal > 0 && movimiento.getCosto() != null && movimiento.getCosto().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal costoTotal = movimiento.getCosto();
                BigDecimal costoUnitario = costoTotal.divide(new BigDecimal(cantidadFinal), 2, RoundingMode.HALF_UP);

                producto.setPrecio_compra(costoUnitario);
                productoDao.guardar(producto); // Guardar manual
            }

            inventario.setStock_actual(inventario.getStock_actual() + cantidadFinal);
            movimiento.setDescripcion("COMPRA: " + movimiento.getCantidad() + " cajas (" + cantidadFinal + " un.)");

        } else if ("Salida".equalsIgnoreCase(tipoAccion)) {
            if (inventario.getStock_actual() < cantidadFinal) {
                model.addAttribute("error", "⛔ STOCK INSUFICIENTE");
                model.addAttribute("movimiento", movimiento);
                model.addAttribute("productos", productoDao.listarTodos());
                return "movimientos/create";
            }

            inventario.setStock_actual(inventario.getStock_actual() - cantidadFinal);

            if (producto.getPrecio_venta() != null) {
                BigDecimal cobro = producto.getPrecio_venta().multiply(new BigDecimal(cantidadFinal));
                movimiento.setCosto(cobro);
            }

            movimiento.setDescripcion("VENTA: " + cantidadFinal + " unidades");
        }

        inventario.setFecha_actualizacion(LocalDateTime.now());

        inventarioDao.guardar(inventario);
        movimientoDao.guardar(movimiento);

        return "redirect:/movimientos";
    }
}