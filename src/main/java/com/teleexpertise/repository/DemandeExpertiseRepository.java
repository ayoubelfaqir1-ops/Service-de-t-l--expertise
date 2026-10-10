package com.teleexpertise.repository;

import com.teleexpertise.config.JpaUtil;
import com.teleexpertise.entity.DemandeExpertise;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DemandeExpertiseRepository {

    public DemandeExpertise create(DemandeExpertise demande) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(demande);

            em.getTransaction().commit();

            return demande;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<DemandeExpertise> findBySpecialisteId(Long specialisteId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT d FROM DemandeExpertise d WHERE d.specialisteId = :specialisteId",
                    DemandeExpertise.class)
                    .setParameter("specialisteId", specialisteId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public Optional<DemandeExpertise> findByConsultationId(Long consultationId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM DemandeExpertise d WHERE d.consultationId = :consultationId",
                    DemandeExpertise.class)
                    .setParameter("consultationId", consultationId)
                    .getResultStream()
                    .findFirst();

        } finally {
            em.close();
        }
    }

    public DemandeExpertise findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(DemandeExpertise.class, id);
        } finally {
            em.close(); // Guarantees the connection is released back to the pool
        }
    }

    public DemandeExpertise update(DemandeExpertise demande) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            DemandeExpertise updated = em.merge(demande);

            em.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); 
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
