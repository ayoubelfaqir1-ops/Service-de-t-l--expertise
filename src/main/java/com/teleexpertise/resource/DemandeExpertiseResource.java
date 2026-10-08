package com.teleexpertise.resource;

import com.teleexpertise.dto.CreateDemandeRequest;
import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.enums.StatutDemandeExpertise;
import com.teleexpertise.service.DemandeExpertiseService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;

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

    @GET
    public Response getDemandes(
            @QueryParam("specialisteId") Long specialisteId,
            @QueryParam("statut") String statut,
            @QueryParam("consultationId") Long consultationId
    )
    {

        // Cas GENERALISTE :
        // recherche d'une demande par consultation
        if (consultationId != null)
        {
            DemandeExpertise demande = service.getByConsultationId(consultationId);
            return (Response.ok(demande).build());
        }

        // Cas SPECIALISTE provisoire
        // TODO: remplacer specialisteId par SecurityContext
        if (specialisteId == null)
        {
            throw new BadRequestException("specialisteId est requis temporairement");
        }

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

        List<DemandeExpertise> demandes =
                service.getDemandesSpecialiste(
                        specialisteId,
                        statutEnum
                );

        return Response.ok(demandes).build();
    }


    @POST
    public Response createDemande(CreateDemandeRequest request){

        DemandeExpertise demande = service.create(request);

        return Response
                .status(Response.Status.CREATED)
                .entity(demande)
                .build();
    }
}
