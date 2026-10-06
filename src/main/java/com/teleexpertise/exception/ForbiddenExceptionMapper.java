package com.teleexpertise.exception;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper
        implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {

        ErrorResponse error = new ErrorResponse(
                403,
                exception.getMessage()
        );

        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(error)
                .build();
    }
}