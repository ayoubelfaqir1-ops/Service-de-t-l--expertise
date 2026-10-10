package com.teleexpertise.resource;

import com.teleexpertise.dto.ExpertiseResponseDTO;
import com.teleexpertise.dto.ExpertiseResponseRequest;
import com.teleexpertise.service.DemandeExpertiseService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import com.teleexpertise.security.AuthenticatedUser;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/demandes/{id}/reponse")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResponseExpertiseResource {

    private final DemandeExpertiseService service = new DemandeExpertiseService();


    @RolesAllowed("SPECIALISTE")
    @PUT
    public Response responeToDemande(@Context SecurityContext sec,@PathParam("id") Long id, ExpertiseResponseRequest request){
        AuthenticatedUser user = (AuthenticatedUser)sec.getUserPrincipal();
        ExpertiseResponseDTO demande = service.response(user.getId(), id, request);;

        return Response
                .ok()
                .entity(demande)
                .build();
    }
}
