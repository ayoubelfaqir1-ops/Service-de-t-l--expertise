package com.teleexpertise.resource;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test/errors")
@Produces(MediaType.APPLICATION_JSON)
public class TestExceptionResource {

    @GET
    @Path("/{type}")
    public Response testError(@PathParam("type") String type) {

        switch (type) {
            case "400":
                throw new BadRequestException("Requête invalide");

            case "403":
                throw new ForbiddenException("Accès interdit");

            case "404":
                throw new NotFoundException("Ressource introuvable");

            default:
                return Response.ok()
                        .entity("{\"message\":\"Aucune erreur\"}")
                        .build();
        }
    }
}