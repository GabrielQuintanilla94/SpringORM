package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_producto;

    private String nombre_producto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    private BigDecimal precio_compra;
    private BigDecimal precio_venta;
    private Integer unidades_por_caja;
    private String unidad_medida;
    private String imagen_url;
    private Boolean estado;


    public Producto() {

        this.categoria = new Categoria();
    }

    // --- GETTERS Y SETTERS ---


    public Integer getId_producto() { return id_producto; }
    public void setId_producto(Integer id_producto) { this.id_producto = id_producto; }

    public String getNombre_producto() { return nombre_producto; }
    public void setNombre_producto(String nombre_producto) { this.nombre_producto = nombre_producto; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public BigDecimal getPrecio_compra() { return precio_compra; }
    public void setPrecio_compra(BigDecimal precio_compra) { this.precio_compra = precio_compra; }

    public BigDecimal getPrecio_venta() { return precio_venta; }
    public void setPrecio_venta(BigDecimal precio_venta) { this.precio_venta = precio_venta; }

    public Integer getUnidades_por_caja() { return unidades_por_caja; }
    public void setUnidades_por_caja(Integer unidades_por_caja) { this.unidades_por_caja = unidades_por_caja; }

    public String getUnidad_medida() { return unidad_medida; }
    public void setUnidad_medida(String unidad_medida) { this.unidad_medida = unidad_medida; }

    public String getImagen_url() { return imagen_url; }
    public void setImagen_url(String imagen_url) { this.imagen_url = imagen_url; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}