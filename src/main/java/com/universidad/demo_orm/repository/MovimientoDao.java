package com.universidad.demo_orm.repository;

import com.universidad.demo_orm.entity.Movimiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class MovimientoDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Movimiento> listarTodos() {
        return em.createQuery("FROM Movimiento", Movimiento.class).getResultList();
    }

    @Transactional
    public void guardar(Movimiento movimiento) {
        if (movimiento.getId_movimiento() != null && movimiento.getId_movimiento() > 0) {
            em.merge(movimiento);
        } else {
            em.persist(movimiento);
        }
    }
}