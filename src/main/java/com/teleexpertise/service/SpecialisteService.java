package com.teleexpertise.service;

import com.teleexpertise.dto.SpecialisteResponseDTO;
import com.teleexpertise.entity.Specialiste;
import com.teleexpertise.enums.Specialite;
import com.teleexpertise.repository.SpecialisteRepository;
import com.teleexpertise.repository.impl.SpecialisteRepositoryImpl;
import jakarta.ws.rs.NotFoundException;

import java.util.Comparator;
import java.util.List;

public class SpecialisteService {

    // On instancie le repository UNE SEULE FOIS pour tout le service
    private final SpecialisteRepository repo = new SpecialisteRepositoryImpl();

    public List<SpecialisteResponseDTO> getAllSpecialistes() {
        return repo.findAll()
                   .stream()
                   .map(SpecialisteResponseDTO::fromEntity)
                   .toList();
    }

    public SpecialisteResponseDTO getSpecialisteById(Long id) {
        Specialiste specialiste = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Spécialiste introuvable avec l'ID: " + id));

        return SpecialisteResponseDTO.fromEntity(specialiste);
    }

    public List<SpecialisteResponseDTO> getSpecialistesFiltresEtTries(Specialite specialite) {
        if (specialite == null) {
            throw new IllegalArgumentException("La spécialité ne peut pas être nulle");
        }

        return repo.findBySpecialite(specialite)
                   .stream()
                   .sorted(Comparator.comparingDouble(Specialiste::getTarif))
                   .map(SpecialisteResponseDTO::fromEntity)
                   .toList();
    }
    public Specialiste getByUtilisateurId(Long utilisateurId) {
        return repo.findByUtilisateurId(utilisateurId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Aucun spécialiste associé à cet utilisateur"
                        )
                );
    }
}
