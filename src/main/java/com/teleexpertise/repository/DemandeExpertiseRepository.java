package com.teleexpertise.repository;

import com.teleexpertise.config.JpaUtil;
import com.teleexpertise.entity.DemandeExpertise;
import jakarta.persistence.EntityManager;

public class DemandeExpertiseRepository {

    public DemandeExpertise create(DemandeExpertise demande){

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(demande);

            em.getTransaction().commit();

            return demande;
        }
        catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            em.close();
        }
    }
}
