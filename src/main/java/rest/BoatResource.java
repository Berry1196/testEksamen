package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatsDTO;
import dtos.HarbourDTO;
import facades.BoatFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("boat")
public class BoatResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BoatFacade FACADE = BoatFacade.getBoatFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getBoats() {
        return GSON.toJson(FACADE.getBoats());
    }

    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{id}")
    public Response deleteBoat(@PathParam("id") Long id) {
        return Response.ok(GSON.toJson(FACADE.deleteBoat(id))).build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addBoat(String boat) {
        BoatsDTO boatDTO = GSON.fromJson(boat, BoatsDTO.class);
        BoatsDTO returnedDTO = FACADE.createBoat(boatDTO);
        return Response.ok(GSON.toJson(returnedDTO)).build();
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response addBoatToHarbour(String boat) {
        BoatsDTO boatDTO = GSON.fromJson(boat, BoatsDTO.class);
        FACADE.addBoatToHarbour(boatDTO.getId(), boatDTO.getHarbour_id());
       return Response.ok().build();
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("edit")
    public Response editBoat( String boat) {
        BoatsDTO boatDTO = GSON.fromJson(boat, BoatsDTO.class);
        BoatsDTO updatedBoat = FACADE.editBoat(boatDTO);
        return Response.ok().entity(updatedBoat).build();
    }
}
