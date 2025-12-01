package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Categoria")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_categoria;

    private String nombre_categoria;
    private String descripcion;
    private Boolean estado;
}