package com.teleexpertise.resource;

import com.teleexpertise.dto.SpecialisteResponseDTO;
import com.teleexpertise.enums.Specialite;
import com.teleexpertise.service.SpecialisteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/specialistes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecialisteResource {

    private final SpecialisteService service = new SpecialisteService();

    /**
     * GET /webapi/specialistes
     * GET /webapi/specialistes?specialite=CARDIOLOGIE
     */
    @GET
    public Response getAllOrFiltered(@QueryParam("specialite") Specialite specialite) {
        List<SpecialisteResponseDTO> result;
        if (specialite != null) {
            result = service.getSpecialistesFiltresEtTries(specialite);
        } else {
            result = service.getAllSpecialistes();
        }
        return Response.ok(result).build();
    }

    /**
     * GET /webapi/specialistes/{id}
     */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        SpecialisteResponseDTO dto = service.getSpecialisteById(id);
        return Response.ok(dto).build();
    }
}
