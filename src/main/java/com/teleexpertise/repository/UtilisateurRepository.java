package com.teleexpertise.repository;

import com.teleexpertise.config.JpaUtil;
import com.teleexpertise.entity.Utilisateur;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class UtilisateurRepository {

    public Optional<Utilisateur> findByEmail(String email) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE u.email = :email",
                            Utilisateur.class
                    )
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();

        } finally {
            em.close();
        }
    }
}