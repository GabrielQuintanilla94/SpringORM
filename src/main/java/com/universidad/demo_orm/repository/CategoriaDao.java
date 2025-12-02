package com.universidad.demo_orm.repository;

import com.universidad.demo_orm.entity.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class CategoriaDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return em.createQuery("FROM Categoria", Categoria.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Integer id) {
        return em.find(Categoria.class, id);
    }
}