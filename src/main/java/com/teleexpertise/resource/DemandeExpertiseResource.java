package com.teleexpertise.resource;

import com.teleexpertise.dto.CreateDemandeRequest;
import com.teleexpertise.entity.DemandeExpertise;
import com.teleexpertise.service.DemandeExpertiseService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DemandeExpertiseResource {

    private final DemandeExpertiseService service = new DemandeExpertiseService();

    @POST
    public Response createDemande(CreateDemandeRequest request){

        DemandeExpertise demande = service.create(request);

        return Response
                .status(Response.Status.CREATED)
                .entity(demande)
                .build();
    }
}
