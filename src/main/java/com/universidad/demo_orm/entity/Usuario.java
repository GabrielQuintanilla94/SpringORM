package com.universidad.demo_orm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    private String nombre_usuario;
    private String usuario_login;
    private String contrasena;
    private String rol;
    private Boolean estado;
}
