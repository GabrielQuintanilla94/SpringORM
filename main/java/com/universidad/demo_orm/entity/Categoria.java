package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Categoria") 
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    private String descripcion;
    private Integer estado;
}