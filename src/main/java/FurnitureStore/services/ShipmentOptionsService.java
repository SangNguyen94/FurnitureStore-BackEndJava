/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.services;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * REST Web Service
 *
 * @author Max
 */

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import FurnitureStore.bo.ShipmentOptionsBO;
import FurnitureStore.dto.ShipmentOptionsDTO;
import FurnitureStore.helper.LocalDateTimeSerializer;

@Stateless
@Path("shipmentOptions")
public class ShipmentOptionsService {

    public ShipmentOptionsService() {
    }
 Gson anotherGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        
		@Override
		public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws com.google.gson.JsonParseException {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
		
		}
    }).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOptions() {
        ShipmentOptionsBO shipmentOptionsBO = new ShipmentOptionsBO();
        ShipmentOptionsDTO result = new ShipmentOptionsDTO();
        try {
            result = shipmentOptionsBO.getShipmentOptions();
            if (result.getImportFilterID() != null && result.getOrderFilterID() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}