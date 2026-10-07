package com.teleexpertise.service;

import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.enums.Priorite;
import com.teleexpertise.enums.StatutDemandeExpertise;
import com.teleexpertise.repository.DemandeExpertiseRepository;
import jakarta.ws.rs.NotFoundException;

import java.util.Comparator;
import java.util.List;

public class DemandeExpertiseService {

    private final DemandeExpertiseRepository repository = new DemandeExpertiseRepository();

    public List<DemandeExpertise> getDemandesSpecialiste(Long specialisteId, StatutDemandeExpertise statut)
    {
        return repository.findBySpecialisteId(specialisteId).stream().filter(demande -> statut == null || demande.getStatut() == statut).sorted(Comparator.comparingInt(demande -> ordrePriorite(demande.getPriorite()))).toList();
    }

    public DemandeExpertise getByConsultationId(Long consultationId)
    {
        return repository.findByConsultationId(consultationId).orElseThrow(() -> new NotFoundException("Demande d'expertise introuvable"));
    }

    private int ordrePriorite(Priorite priorite) {
        return switch (priorite) {
            case URGENTE -> 1;
            case NORMALE -> 2;
            case NON_URGENTE -> 3;
        };
    }
}