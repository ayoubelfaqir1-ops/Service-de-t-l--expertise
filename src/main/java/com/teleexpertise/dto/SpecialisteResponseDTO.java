package com.teleexpertise.dto;

import com.teleexpertise.enums.Specialite;
import com.teleexpertise.entity.Specialiste;


public record SpecialisteResponseDTO(Long id, Long utilisateurId, Specialite specialite, double tarif) {
    public static SpecialisteResponseDTO fromEntity(Specialiste s) {
        return new SpecialisteResponseDTO(s.getId(), s.getUtilisateurId(), s.getSpecialite(), s.getTarif());
    }
}


