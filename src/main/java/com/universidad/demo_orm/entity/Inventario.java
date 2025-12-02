package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inventario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    private Integer stock_actual;
    private Integer stock_minimo;
    private LocalDateTime fecha_actualizacion;

    public Inventario() {}

    // Getters y Setters manuales
    public Integer getId_inventario() { return id_inventario; }
    public void setId_inventario(Integer id_inventario) { this.id_inventario = id_inventario; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getStock_actual() { return stock_actual; }
    public void setStock_actual(Integer stock_actual) { this.stock_actual = stock_actual; }

    public Integer getStock_minimo() { return stock_minimo; }
    public void setStock_minimo(Integer stock_minimo) { this.stock_minimo = stock_minimo; }

    public LocalDateTime getFecha_actualizacion() { return fecha_actualizacion; }
    public void setFecha_actualizacion(LocalDateTime fecha_actualizacion) { this.fecha_actualizacion = fecha_actualizacion; }
}