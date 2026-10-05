package com.teleexpertise.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

/**
 * Health check endpoint to verify API and container readiness.
 * Exposed at /webapi/health
 */
@Path("/health")
public class PingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHealth() {
        return Response.ok(Map.of(
            "status", "UP",
            "service", "tele-expertise-api"
        )).build();
    }
}
