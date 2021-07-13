/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.services;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import FurnitureStore.bo.OrderShipmentBO;
import FurnitureStore.dto.OrderDTO;
import FurnitureStore.dto.OrderShipmentDTO;
import FurnitureStore.dto.OrderShipmentEnvelopeDTO;
import FurnitureStore.helper.LocalDateTimeSerializer;

@Stateless
@Path("orderShipment")
public class OrderShipmentService {

    public OrderShipmentService() {
    }
 Gson anotherGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        
		@Override
		public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws com.google.gson.JsonParseException {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
		
		}
    }).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String entity) {
        try {
            OrderShipmentBO OrderShipmentBO = new OrderShipmentBO();
            OrderShipmentDTO OrderShipmentDTO = anotherGson.fromJson(entity, OrderShipmentDTO.class);
            if (OrderShipmentBO.createShipment(OrderShipmentDTO)) {
                return Response.ok().build();
            }
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
            OrderShipmentBO OrderShipmentBO = new OrderShipmentBO();
            OrderShipmentDTO OrderShipmentDTO = anotherGson.fromJson(entity, OrderShipmentDTO.class);
            if (OrderShipmentBO.editShipment(id, OrderShipmentDTO))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        try {
            OrderShipmentBO OrderShipmentBO = new OrderShipmentBO();
            if (OrderShipmentBO.removeShipment(id))
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
        OrderShipmentBO OrderShipmentBO = new OrderShipmentBO();
        OrderShipmentDTO result = new OrderShipmentDTO();
        try {
            result = OrderShipmentBO.getShipmentById(id);
            if (result.getId() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response findAll() {
    // try {
    // OrderShipmentBO OrderShipmentBO = new OrderShipmentBO();
    // OrderShipmentEnvelopeDTO result = new OrderShipmentEnvelopeDTO();
    // result.setProducts(OrderShipmentBO.getAllProducts());
    // if (!result.getProducts().isEmpty())
    // return Response.ok().entity(result).build();
    // } catch (Exception e) {
    // //
    // }
    // return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error
    // getting item product").build();
    // }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterOrderShipment(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
            @QueryParam("orderID") Integer orderID, @QueryParam("deliverDate") String deliverDate,
            @QueryParam("shipmentID") String shipmentID, @QueryParam("shipmentCompany") String shipmentCompany,
            @QueryParam("shipmentStatus") String shipmentStatus) {
        OrderShipmentBO OrderShipmentBO = new OrderShipmentBO();
        ObjectMapper mapper = JsonMapper.builder()
    		    .addModule(new JavaTimeModule())
    		    .build();
        OrderShipmentEnvelopeDTO result = new OrderShipmentEnvelopeDTO();
        try {
            result = OrderShipmentBO.getFilteredShipment(offset, limit, orderID, deliverDate, shipmentID,
                    shipmentCompany, shipmentStatus);
            if (result.getShipments() != null) {

                return Response.ok().entity(anotherGson.toJson(result)).build();
            }
        } catch (Exception e) {
            //
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}