package com.universidad.demo_orm.repository;

import com.universidad.demo_orm.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    @Query("SELECT u FROM Usuario u WHERE u.usuario_login = ?1")
    Optional<Usuario> findByUsuarioLogin(String login);
}