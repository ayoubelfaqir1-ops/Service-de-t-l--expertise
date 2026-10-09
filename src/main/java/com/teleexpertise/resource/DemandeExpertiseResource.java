package com.teleexpertise.resource;

import com.teleexpertise.dto.CreateDemandeRequest;
import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.enums.StatutDemandeExpertise;
import com.teleexpertise.service.DemandeExpertiseService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import com.teleexpertise.entity.Specialiste;
import com.teleexpertise.security.AuthenticatedUser;
import com.teleexpertise.service.SpecialisteService;
import jakarta.ws.rs.ForbiddenException;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import java.util.List;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class DemandeExpertiseResource {

    private final DemandeExpertiseService service = new DemandeExpertiseService();
    private final SpecialisteService specialisteService = new SpecialisteService();

    @GET
    @RolesAllowed({"GENERALISTE", "SPECIALISTE"})
    public Response getDemandes(
            @QueryParam("statut") String statut,
            @QueryParam("consultationId") Long consultationId,
            @Context SecurityContext securityContext
    ) {

        // Cas 1 : recherche par consultation
        // Réservée au GENERALISTE
        if (consultationId != null) {

            if (!securityContext.isUserInRole("GENERALISTE")) {
                throw new ForbiddenException(
                        "Seul un généraliste peut consulter une demande par consultation"
                );
            }

            DemandeExpertise demande =
                    service.getByConsultationId(consultationId);

            return Response.ok(demande).build();
        }

        // Cas 2 : consultation des demandes du spécialiste connecté
        // Réservée au SPECIALISTE
        if (!securityContext.isUserInRole("SPECIALISTE")) {
            throw new ForbiddenException(
                    "Seul un spécialiste peut consulter cette liste de demandes"
            );
        }

        // 1. Récupérer l'utilisateur authentifié
        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) securityContext.getUserPrincipal();

        // 2. retrouver le spécialiste lié à cet utilisateur
        Specialiste specialiste =
                specialisteService.getByUtilisateurId(
                        authenticatedUser.getId()
                );

        // 3. convertir le statut reçu dans l'URL
        StatutDemandeExpertise statutEnum = null;

        if (statut != null) {
            try {
                statutEnum = StatutDemandeExpertise.valueOf(
                        statut.toUpperCase()
                );
            } catch (IllegalArgumentException e) {
                throw new BadRequestException(
                        "Statut invalide : " + statut
                );
            }
        }

        // 4. récupérer uniquement les demandes
        // du spécialiste actuellement connecté
        List<DemandeExpertise> demandes =
                service.getDemandesSpecialiste(
                        specialiste.getId(),
                        statutEnum
                );

        return Response.ok(demandes).build();
    }


    @RolesAllowed("GENERALISTE")
    @POST
    public Response createDemande(CreateDemandeRequest request){

        DemandeExpertise demande = service.create(request);

        return Response
                .status(Response.Status.CREATED)
                .entity(demande)
                .build();
    }
}
