package com.teleexpertise.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException>
{
    @Override
    public Response toResponse(BadRequestException exception)
    {
        ErrorResponse error = new ErrorResponse(400,exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
