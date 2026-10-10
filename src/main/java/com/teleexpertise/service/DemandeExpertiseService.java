package com.teleexpertise.service;

import com.teleexpertise.dto.CreateDemandeRequest;
import com.teleexpertise.dto.ExpertiseResponseDTO;
import com.teleexpertise.dto.ExpertiseResponseRequest;
import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.entity.Specialiste;
import com.teleexpertise.entity.Utilisateur;
import com.teleexpertise.enums.Priorite;
import com.teleexpertise.enums.StatutDemandeExpertise;
import com.teleexpertise.repository.ConsultationRepository;
import com.teleexpertise.repository.DemandeExpertiseRepository;
import com.teleexpertise.repository.SpecialisteRepository;
import com.teleexpertise.repository.impl.SpecialisteRepositoryImpl;
import com.teleexpertise.security.AuthenticatedUser;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.security.Security;
import java.util.Comparator;
import java.util.List;

public class DemandeExpertiseService {

    SpecialisteRepository specialisteRepository = new SpecialisteRepositoryImpl();
    ConsultationRepository consultationRepository = new ConsultationRepository();
    DemandeExpertiseRepository demandeExpertiseRepository = new DemandeExpertiseRepository();

    public DemandeExpertise create(CreateDemandeRequest request) {

        // check
        if (request.getQuestion() == null || request.getQuestion().isBlank()) {
            throw new BadRequestException("La question est obligatoire");
        }

        if (request.getPriorite() == null) {
            throw new BadRequestException("la priorite est obligatoire");
        }

        if (request.getSpecialisteId() == null) {
            throw new BadRequestException("Le specialiste est obligatoire");
        }

        if (request.getConsultationId() == null) {
            throw new BadRequestException("La consultation est obligatoire");
        }

        if (specialisteRepository.findById(request.getSpecialisteId()) == null) {
            throw new NotFoundException("Specialiste introuvable");
        }

        if (consultationRepository.findById(request.getConsultationId()) == null) {
            throw new NotFoundException("Consultation introuvable");
        }

        // creation demande
        DemandeExpertise demande = new DemandeExpertise(
                request.getConsultationId(),
                request.getSpecialisteId(),
                request.getQuestion(),
                request.getPriorite());

        return demandeExpertiseRepository.create(demande);
    }

    public List<DemandeExpertise> getDemandesSpecialiste(Long specialisteId, StatutDemandeExpertise statut) {
        return demandeExpertiseRepository.findBySpecialisteId(specialisteId).stream()
                .filter(demande -> statut == null || demande.getStatut() == statut)
                .sorted(Comparator.comparingInt(demande -> ordrePriorite(demande.getPriorite()))).toList();
    }

    public DemandeExpertise getByConsultationId(Long consultationId) {
        return demandeExpertiseRepository.findByConsultationId(consultationId)
                .orElseThrow(() -> new NotFoundException("Demande d'expertise introuvable"));
    }

    private int ordrePriorite(Priorite priorite) {
        return switch (priorite) {
            case URGENTE -> 1;
            case NORMALE -> 2;
            case NON_URGENTE -> 3;
        };
    }

    public ExpertiseResponseDTO response(long utilisateurId, Long id, ExpertiseResponseRequest request) {
        DemandeExpertise demande = demandeExpertiseRepository.findById(id);
        Specialiste specialiste = specialisteRepository.findByUtilisateurId(utilisateurId).orElseThrow(() -> new ForbiddenException("Profil spécialiste introuvable pour cet utilisateur"));
        if (demande == null) {
            throw new NotFoundException("Demande d'expertise introuvable avec l'id : " + id);
        } else if (!demande.getSpecialisteId().equals(specialiste.getId())) {
            throw new ForbiddenException("Vous n'êtes pas le spécialiste assigné à cette demande d'expertise.");
        }

        //input validation
        if (request.getAvis() == null || request.getAvis().isBlank()) {
            throw new BadRequestException("L'avis médical est obligatoire");
        }

        demande.repondre(request.getAvis(), request.getRecommandations());
        DemandeExpertise updatedDemande = demandeExpertiseRepository.update(demande);
        return ExpertiseResponseDTO.fromEntity(updatedDemande);

    }
}
