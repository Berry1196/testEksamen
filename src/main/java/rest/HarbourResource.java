package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.HarbourFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("harbour")
public class HarbourResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HarbourFacade FACADE = HarbourFacade.getHarbourFacade(EMF);

    @GET
    public String getHarbours() {
        return GSON.toJson(FACADE.getAllHabours());
    }

    @DELETE
    @Path("{id}")
    public Response deleteHarbour(@PathParam("id") Long id) {
        return Response.ok(GSON.toJson(FACADE.deleteHarbour(id))).build();
    }
}

