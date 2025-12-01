package com.universidad.demo_orm.repository;

import com.universidad.demo_orm.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    
    @Query("SELECT i FROM Inventario i WHERE i.producto.id_producto = ?1")
    Optional<Inventario> findByProducto_IdProducto(Integer idProducto);
}