package com.universidad.demo_orm.repository;

import com.universidad.demo_orm.entity.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ProductoDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return em.createQuery("FROM Producto p WHERE p.estado = true", Producto.class).getResultList();
    }

    @Transactional
    public void guardar(Producto producto) {
        if (producto.getId_producto() != null && producto.getId_producto() > 0) {
            em.merge(producto);
        } else {
            
            if(producto.getEstado() == null) producto.setEstado(true);
            em.persist(producto);
        }
    }

    @Transactional(readOnly = true)
    public Producto buscarPorId(Integer id) {
        return em.find(Producto.class, id);
    }


    @Transactional
    public void eliminar(Integer id) {
        Producto p = buscarPorId(id);
        if (p != null) {
            p.setEstado(false);
            em.merge(p);
        }
    }
}