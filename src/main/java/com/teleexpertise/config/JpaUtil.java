package com.teleexpertise.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Thread-safe utility managing the lifecycle of the JPA EntityManagerFactory.
 * In a Tomcat servlet container, JPA is not managed automatically by the container,
 * so we maintain a single, application-wide EntityManagerFactory instance.
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "teleExpertisePU";
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    private JpaUtil() {
        // Prevent instantiation
    }

    /**
     * @return the shared, thread-safe EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    /**
     * Creates and returns a new EntityManager.
     * Note: EntityManager is NOT thread-safe. Each thread or request should open and close its own.
     *
     * @return a new EntityManager instance
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Closes the EntityManagerFactory on application shutdown to release database connections.
     */
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
