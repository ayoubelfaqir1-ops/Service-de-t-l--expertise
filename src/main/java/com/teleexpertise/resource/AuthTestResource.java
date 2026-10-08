package com.teleexpertise.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/auth-test")
@Produces(MediaType.APPLICATION_JSON)
public class AuthTestResource {

    @GET
    @Path("/specialiste")
    @RolesAllowed("SPECIALISTE")
    public Response specialisteOnly() {
        return Response.ok(
                Map.of("message", "Accès spécialiste autorisé")
        ).build();
    }

    @GET
    @Path("/generaliste")
    @RolesAllowed("GENERALISTE")
    public Response generalisteOnly() {
        return Response.ok(
                Map.of("message", "Accès généraliste autorisé")
        ).build();
    }
}