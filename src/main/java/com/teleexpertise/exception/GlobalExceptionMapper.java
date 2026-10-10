package com.teleexpertise.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        // 1. Log the full stack trace to the console for debugging
        exception.printStackTrace();

        // 2. If it's a known JAX-RS WebApplicationException (like 405 Method Not Allowed)
        // preserve its original HTTP status code
        if (exception instanceof WebApplicationException webAppException) {
            int status = webAppException.getResponse().getStatus();
            String message = exception.getMessage() != null ? exception.getMessage() : "Erreur de requête";
            return Response.status(status)
                    .entity(new ErrorResponse(status, message))
                    .build();
        }

        // 3. Fallback for all unexpected errors (SQL, NPE, etc.) -> Clean 500 JSON
        String message = exception.getMessage() != null && !exception.getMessage().isBlank()
                ? exception.getMessage()
                : "Une erreur interne est survenue sur le serveur";

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(500, message))
                .build();
    }
}
