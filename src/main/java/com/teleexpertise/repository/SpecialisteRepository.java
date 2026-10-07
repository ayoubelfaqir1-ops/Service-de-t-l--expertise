package com.teleexpertise.repository;

import com.teleexpertise.config.JpaUtil;
import com.teleexpertise.entity.Specialiste;
import jakarta.persistence.EntityManager;

public class SpecialisteRepository {

    public Specialiste findById(Long id){

        EntityManager em = JpaUtil.getEntityManager();

        try {
           return em.find(Specialiste.class, id);
        }
        finally {
            em.close();
        }
    }
}
