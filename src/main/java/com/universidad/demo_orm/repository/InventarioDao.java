package com.universidad.demo_orm.repository;

import com.universidad.demo_orm.entity.Inventario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class InventarioDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public Inventario buscarPorProductoId(Integer idProducto) {
        List<Inventario> lista = em.createQuery("SELECT i FROM Inventario i WHERE i.producto.id_producto = :pid", Inventario.class)
                .setParameter("pid", idProducto)
                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Transactional
    public void guardar(Inventario inventario) {
        if (inventario.getId_inventario() != null && inventario.getId_inventario() > 0) {
            em.merge(inventario);
        } else {
            em.persist(inventario);
        }
    }
}