package com.teleexpertise.service;

import com.teleexpertise.dto.CreateDemandeRequest;
import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.enums.Priorite;
import com.teleexpertise.enums.StatutDemandeExpertise;
import com.teleexpertise.repository.ConsultationRepository;
import com.teleexpertise.repository.DemandeExpertiseRepository;
import com.teleexpertise.repository.SpecialisteRepository;
import com.teleexpertise.repository.impl.SpecialisteRepositoryImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.Comparator;
import java.util.List;

public class DemandeExpertiseService {

    SpecialisteRepository specialisteRepository = new SpecialisteRepositoryImpl();
    ConsultationRepository consultationRepository = new ConsultationRepository();
    DemandeExpertiseRepository demandeExpertiseRepository = new DemandeExpertiseRepository();

    public DemandeExpertise create(CreateDemandeRequest request){

        //check
        if(request.getQuestion() == null || request.getQuestion().isBlank()){
            throw new BadRequestException("La question est obligatoire");
        }

        if(request.getPriorite() == null){
            throw new BadRequestException("la priorite est obligatoire");
        }

        if(request.getSpecialisteId() == null){
            throw new BadRequestException("Le specialiste est obligatoire");
        }

        if (request.getConsultationId() == null) {
            throw new BadRequestException("La consultation est obligatoire");
        }

        if(specialisteRepository.findById(request.getSpecialisteId()) == null){
            throw new NotFoundException("Specialiste introuvable");
        }

        if(consultationRepository.findById(request.getConsultationId()) == null){
            throw new NotFoundException("Consultation introuvable");
        }

        //creation demande
        DemandeExpertise demande = new DemandeExpertise(
                request.getConsultationId(),
                request.getSpecialisteId(),
                request.getQuestion(),
                request.getPriorite()
        );

        return demandeExpertiseRepository.create(demande);
    }

    public List<DemandeExpertise> getDemandesSpecialiste(Long specialisteId, StatutDemandeExpertise statut)
    {
        return demandeExpertiseRepository.findBySpecialisteId(specialisteId).stream().filter(demande -> statut == null || demande.getStatut() == statut).sorted(Comparator.comparingInt(demande -> ordrePriorite(demande.getPriorite()))).toList();
    }

    public DemandeExpertise getByConsultationId(Long consultationId)
    {
        return demandeExpertiseRepository.findByConsultationId(consultationId).orElseThrow(() -> new NotFoundException("Demande d'expertise introuvable"));
    }

    private int ordrePriorite(Priorite priorite) {
        return switch (priorite) {
            case URGENTE -> 1;
            case NORMALE -> 2;
            case NON_URGENTE -> 3;
        };
    }
}
