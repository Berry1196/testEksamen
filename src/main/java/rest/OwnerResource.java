package rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import dtos.OwnerDTO;
import facades.OwnerFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("owner")
public class OwnerResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OwnerFacade FACADE = OwnerFacade.getOwnerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getOwners() {
        return GSON.toJson(FACADE.getOwners());
    }

    @POST
    @Produces("application/json")
    public String createOwner(String owner) {
        OwnerDTO ownerDTO = GSON.fromJson(owner, OwnerDTO.class);
        OwnerDTO returnedDTO = FACADE.createOwner(ownerDTO);
        return GSON.toJson(returnedDTO);
    }
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("edit")
    public Response editOwner(String owner) {
        OwnerDTO ownerDTO = GSON.fromJson(owner, OwnerDTO.class);
        OwnerDTO updatedOwner = FACADE.editOwner(ownerDTO);

        return Response.ok().entity(updatedOwner).build();
    }
}
