/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.services;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * REST Web Service
 *
 * @author Max
 */

import javax.ejb.Stateless;
import javax.ws.rs.GET;

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

import FurnitureStore.bo.StatisticsBO;
import FurnitureStore.dto.IncomeDTO;
import FurnitureStore.dto.IncomeEnvelopeDTO;
import FurnitureStore.dto.RevenueDTO;
import FurnitureStore.dto.RevenueEnvelopeDTO;
import FurnitureStore.helper.LocalDateTimeSerializer;

@Stateless
@Path("statistics")
public class StatisticsService {

    public StatisticsService() {

    }
 Gson anotherGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        
		@Override
		public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws com.google.gson.JsonParseException {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
		
		}
    }).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();
    @GET
    @Path("income")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIncomes(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
            @QueryParam("begin") String begin, @QueryParam("end") String end, @QueryParam("id") Integer id) {
        StatisticsBO statisticsBO = new StatisticsBO();
        IncomeEnvelopeDTO result = null;
        try {
            result = statisticsBO.getIncomes(offset, limit, begin, end, id);
            if (result.getIncomes() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("revenue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRevenues(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
            @QueryParam("begin") String begin, @QueryParam("end") String end, @QueryParam("id") Integer id) {
        StatisticsBO statisticsBO = new StatisticsBO();
        RevenueEnvelopeDTO result = null;
        try {
            result = statisticsBO.getRevenues(offset, limit, begin, end, id);
            if (result.getRevenues() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}