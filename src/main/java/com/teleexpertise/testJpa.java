package com.teleexpertise;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class testJpa {

    public static void main(String[] args) {

        EntityManagerFactory emf = null;

        try {
            emf = Persistence.createEntityManagerFactory("teleExpertisePU");

            System.out.println("JPA/Hibernate OK - mapping valide");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}