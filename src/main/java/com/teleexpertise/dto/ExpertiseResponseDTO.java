package com.teleexpertise.dto;

import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.enums.StatutDemandeExpertise;

import java.time.LocalDateTime;

public record ExpertiseResponseDTO(
    Long id,
    Long consultationId,
    Long specialisteId,
    String question,
    String avis,
    String recommandations,
    StatutDemandeExpertise statut,
    LocalDateTime dateCreation,
    LocalDateTime dateReponse
) {
    public static ExpertiseResponseDTO fromEntity(DemandeExpertise d) {
        return new ExpertiseResponseDTO(
            d.getId(),
            d.getConsultationId(),
            d.getSpecialisteId(),
            d.getQuestion(),
            d.getAvis(),
            d.getRecommandations(),
            d.getStatut(),
            d.getDateCreation(),
            d.getDateReponse()
        );
    }
}


