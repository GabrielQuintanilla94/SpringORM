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
    private Integer id_producto;

    private String nombre_producto;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    private BigDecimal precio_compra;
    private BigDecimal precio_venta;

    // Campos nuevos necesarios para la lógica de Cajas
    private Integer unidades_por_caja;
    private String unidad_medida;
    private String imagen_url;
    private Boolean estado;
}