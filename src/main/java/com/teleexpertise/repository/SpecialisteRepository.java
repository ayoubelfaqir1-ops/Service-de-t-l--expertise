package com.teleexpertise.repository;

import com.teleexpertise.entity.Specialiste;
import com.teleexpertise.enums.Specialite;

import java.util.List;
import java.util.Optional;

public interface SpecialisteRepository {
    Specialiste save(Specialiste specialiste);
    Optional<Specialiste> findById(Long id);
    List<Specialiste> findAll();
    List<Specialiste> findBySpecialite(Specialite specialite);
    Optional<Specialiste> findByUtilisateurId(Long utilisateurId);
    void delete(Specialiste specialite);
}
