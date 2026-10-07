package com.teleexpertise.repository;

import com.teleexpertise.config.JpaUtil;
import com.teleexpertise.entity.Consultation;
import jakarta.persistence.EntityManager;

public class ConsultationRepository {

    public Consultation findById(Long id){

        EntityManager em = JpaUtil.getEntityManager();

        try{
          return em.find(Consultation.class, id);
        }
        finally {
            em.close();
        }
    }
}
