package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Movimiento")
@Data
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_movimiento;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    private Integer cantidad;
    private String tipo_movimiento; // 'Entrada' o 'Salida'
    private String descripcion;
    
    private LocalDateTime fecha_movimiento;
    
    private BigDecimal costo;
    
    private Integer id_usuario; 
}