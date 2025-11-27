package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal; 

@Entity
@Table(name = "Producto")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @ManyToOne 
    @JoinColumn(name = "id_categoria") 
    private Categoria categoria;

    @Column(name = "precio_compra")
    private BigDecimal precioCompra;

    @Column(name = "precio_venta")
    private BigDecimal precioVenta;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "imagen_url")
    private String imagenUrl;

    private Integer estado;
}