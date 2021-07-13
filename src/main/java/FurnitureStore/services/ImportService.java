package FurnitureStore.services;

import FurnitureStore.bo.ImportBO;
import FurnitureStore.dto.CartDTO;
import FurnitureStore.dto.ImportDTO;
import FurnitureStore.dto.ImportEnvelopeDTO;
import FurnitureStore.helper.IntegerTypeAdapter;
import FurnitureStore.helper.LocalDateTimeSerializer;
import FurnitureStore.helper.Authentication.Role;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

@Stateless
@Path("imports")
public class ImportService {
	
    public ImportService() {
    }

 Gson anotherGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        
		@Override
		public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws com.google.gson.JsonParseException {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
		
		}
    }).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).registerTypeAdapter(Integer.class,new IntegerTypeAdapter()).create();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String entity) {
        try {
            ImportBO importBO = new ImportBO();
            ImportDTO ImportDTO = anotherGson.fromJson(entity, ImportDTO.class);
            if (importBO.createImport(ImportDTO))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, String entity) {
        try {
            ImportBO importBO = new ImportBO();
            ImportDTO ImportDTO = anotherGson.fromJson(entity, ImportDTO.class);
            if (ImportDTO.getProducts() != null || !ImportDTO.getProducts().isEmpty()) {
                if (importBO.editImportedProduct(id, ImportDTO)) {
                    if (importBO.editImport(id, ImportDTO)) {
                        return Response.ok().build();
                    }
                }
            } else {
                if (importBO.editImport(id, ImportDTO)) {
                    return Response.ok().build();
                }
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        try {
            ImportBO importBO = new ImportBO();
            System.out.println(id);
            System.out.println(importBO.removeImport(id));
            if (importBO.removeImport(id))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
        ImportBO importBO = new ImportBO();
        ImportDTO result = new ImportDTO();
        try {
            result = importBO.getImportById(id);
            if (result != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterImports(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
            @QueryParam("name") String name, @QueryParam("address") String address, @QueryParam("phone") String phone,
            @QueryParam("date") String placementDate, @QueryParam("status") String status) {
        ImportBO importBO = new ImportBO();
        ImportEnvelopeDTO result = new ImportEnvelopeDTO();
        try {
            result = importBO.getFilteredImports(offset, limit, name, address, phone, placementDate, status);
            if (result.getImports() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            //
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}