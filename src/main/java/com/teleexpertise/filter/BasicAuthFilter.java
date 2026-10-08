package com.teleexpertise.filter;

import com.teleexpertise.exception.ErrorResponse;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private static final String BASIC_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null
                || !authorizationHeader.startsWith(BASIC_PREFIX)) {

            abortUnauthorized(
                    requestContext,
                    "Identifiants manquants"
            );

            return;
        }

        String encodedCredentials =
                authorizationHeader.substring(BASIC_PREFIX.length());

        try {
            String credentials = new String(
                    Base64.getDecoder().decode(encodedCredentials),
                    StandardCharsets.UTF_8
            );

            String[] parts = credentials.split(":", 2);

            if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank())
            {
                abortUnauthorized(requestContext, "Identifiants invalides");
                return;
            }

            String email = parts[0];
            String password = parts[1];

            // Prochaine étape :
            // rechercher l'utilisateur et vérifier password avec bcrypt.

        } catch (IllegalArgumentException e) {

            abortUnauthorized(
                    requestContext,
                    "Authorization Basic invalide"
            );
        }
    }

    private void abortUnauthorized(
            ContainerRequestContext requestContext,
            String message
    ) {

        ErrorResponse error =
                new ErrorResponse(401, message);

        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(error)
                        .build()
        );
    }
}