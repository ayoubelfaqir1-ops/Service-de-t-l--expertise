package com.teleexpertise.repository.impl;

import com.teleexpertise.config.JpaUtil;
import com.teleexpertise.entity.Specialiste;
import com.teleexpertise.enums.Specialite;
import com.teleexpertise.repository.SpecialisteRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class SpecialisteRepositoryImpl implements SpecialisteRepository {

    public SpecialisteRepositoryImpl() {
        // Constructeur simple sans paramètre
    }

    @Override
    public Specialiste save(Specialiste specialiste) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Specialiste saved;
            if (specialiste.getId() == null) {
                em.persist(specialiste);
                saved = specialiste;
            } else {
                saved = em.merge(specialiste);
            }
            em.getTransaction().commit();
            return saved;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Specialiste> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Specialiste.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Specialiste> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Specialiste s", Specialiste.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Specialiste> findBySpecialite(Specialite specialite) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Specialiste s WHERE s.specialite = :specialite", Specialiste.class)
                     .setParameter("specialite", specialite)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Specialiste> findByUtilisateurId(Long utilisateurId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Specialiste s WHERE s.utilisateurId = :utilisateurId", Specialiste.class)
                     .setParameter("utilisateurId", utilisateurId)
                     .getResultStream()
                     .findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Specialiste specialite) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(specialite) ? specialite : em.merge(specialite));
            em.getTransaction().commit();
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
