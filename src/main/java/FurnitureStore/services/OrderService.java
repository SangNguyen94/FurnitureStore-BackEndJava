package FurnitureStore.services;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import FurnitureStore.helper.LocalDateTimeSerializer;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import FurnitureStore.bo.OrderBO;
import FurnitureStore.dto.ImportShipmentDTO;
import FurnitureStore.dto.OrderDTO;
import FurnitureStore.dto.OrderEnvelopeDTO;
import FurnitureStore.helper.Authentication.Role;

@Stateless
@Path("orders")
public class OrderService {

    public OrderService() {
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
            OrderBO orderBO = new OrderBO();
            OrderDTO OrderDTO = anotherGson.fromJson(entity, OrderDTO.class);
            if (orderBO.createOrder(OrderDTO))
            {
            	
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
            OrderBO orderBO = new OrderBO();
            OrderDTO OrderDTO = anotherGson.fromJson(entity, OrderDTO.class);
            if (OrderDTO.getProducts() != null || !OrderDTO.getProducts().isEmpty()) {
                if (orderBO.editOrderedProduct(id, OrderDTO)) {
                    if (orderBO.editOrder(id, OrderDTO)) {
                        return Response.ok().build();
                    }
                }
            } else {
                if (orderBO.editOrder(id, OrderDTO)) {
                    return Response.ok().build();
                }
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // @PUT
    // @Path("{id}/manage")
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response editOrderedProduct(@PathParam("id") Integer id, OrderDTO
    // entity) {
    // try {
    // OrderBO orderBO = new OrderBO();
    // if (orderBO.editOrderedProduct(id, entity))
    // return Response.ok().build();
    // } catch (Exception e) {
    // return
    // Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
    // }
    // return Response.status(Response.Status.BAD_REQUEST).build();
    // }


    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        try {
            OrderBO orderBO = new OrderBO();
            if (orderBO.removeOrder(id))
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
    	ObjectMapper mapper = JsonMapper.builder()
    		    .addModule(new JavaTimeModule())
    		    .build();
    		// or, 2.x before 2.9
    	
        OrderBO orderBO = new OrderBO();
        OrderDTO result = new OrderDTO();
        try {
            result = orderBO.getOrderById(id);
            if (result != null)
                return Response.ok().entity(anotherGson.toJson(result,OrderDTO.class)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response findAll() {
    // try {
    // ProductBO productBO = new ProductBO();
    // ProductEnvelopeDTO result = new ProductEnvelopeDTO();
    // result.setProducts(productBO.getAllProducts());
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
    public Response filterOrder(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
            @QueryParam("name") String name, @QueryParam("address") String address, @QueryParam("phone") String phone,
            @QueryParam("date") String placementDate, @QueryParam("status") String status) {
        OrderBO orderBO = new OrderBO();
        ObjectMapper mapper = JsonMapper.builder()
    		    .addModule(new JavaTimeModule())
    		    .build();
        OrderEnvelopeDTO result = new OrderEnvelopeDTO();
        System.out.println("limit: "+limit);
        try {
            result = orderBO.getFilteredOrders(offset, limit, name, address, phone, placementDate, status);
            if (result.getOrders() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            //
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}