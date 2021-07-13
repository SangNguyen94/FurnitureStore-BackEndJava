package FurnitureStore.services;

import FurnitureStore.bo.CartBO;
import FurnitureStore.bo.ImportBO;
import FurnitureStore.dto.CartDTO;
import FurnitureStore.dto.CartEnvelopeDTO;
import FurnitureStore.dto.ImportDTO;
import FurnitureStore.dto.ImportEnvelopeDTO;
import FurnitureStore.dto.ProductDTO;
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
import com.google.gson.JsonParser;

@Stateless
@Path("cart")
public class CartService {
	
    public CartService() {
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
    public Response createCart(String entity) {
        try {
            CartBO cartBO = new CartBO();
            CartDTO cartDTO = anotherGson.fromJson(entity, CartDTO.class);
            
          
            if (cartBO.createCart(cartDTO))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Path("{cartID}/{productID}/{quantity}")
    public Response createCartProduct(@PathParam("cartID") Integer cartID,@PathParam("productID") Integer productID,@PathParam("quantity") Integer quantity) {
        try {
            CartBO cartBO = new CartBO();
            if (cartBO.createCartedProduct(cartID, productID, quantity))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Path("{cartID}/{productID}/{quantity}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCartProduct(@PathParam("cartID") Integer cartID,@PathParam("productID") Integer productID,@PathParam("quantity") Integer quantity) {
        try {
            CartBO cartBO = new CartBO();
            if (cartBO.updateCartedProduct(cartID, productID, quantity))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Path("{cartID}/{productID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCartProduct(@PathParam("cartID") Integer cartID,@PathParam("productID") Integer productID) {
        try {
            CartBO cartBO = new CartBO();
            System.out.println(cartID.toString()+" "+productID.toString());
            if (cartBO.removeCartedProduct(cartID, productID))
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
            CartBO cartBO = new CartBO();
            CartDTO cartDTO = anotherGson.fromJson(entity, CartDTO.class);
//            if (entity.getProducts() != null || !entity.getProducts().isEmpty()) {
//                if (cartBO.editCartedProduct(id, entity)) {
//                    if (cartBO.editCart(id, entity)) {
//                        return Response.ok().build();
//                    }
//                }
//            } else {
            System.out.println(cartDTO.getTotalAmount());
                if (cartBO.editCart(id, cartDTO)) {
                    return Response.ok().build();
                }
//        }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

 
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        try {
            CartBO cartBO = new CartBO();
            if (cartBO.removeCart(id))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Path("/CPremove/{id}")
    public Response removeCP(@PathParam("id") Integer id) {
        System.out.println("remove id: "+id);
    	try {
    		System.out.println("jumped here");
            CartBO cartBO = new CartBO();
            if (cartBO.removeCartProductAll(id))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Integer id) {
    
        CartBO cartBO = new CartBO();
        CartDTO result = new CartDTO();
        try {
          result = cartBO.getCartById(id);
            if (anotherGson.toJson(result) != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/count/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCount(@PathParam("id") Integer id) {
    
        CartBO cartBO = new CartBO();
    
        try {
            Integer result = cartBO.cartCount(id);
            System.out.println(anotherGson.toJson(result));
            if (result != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response filterCarts(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
//            @QueryParam("name") String name, @QueryParam("address") String address, @QueryParam("phone") String phone,
//            @QueryParam("date") String placementDate, @QueryParam("status") String status) {
//        CartBO cartBO = new CartBO();
//        CartEnvelopeDTO anotherGson.toJson(result) = new CartEnvelopeDTO();
//        try {
//            anotherGson.toJson(result) = cartBO.getFilteredCarts(offset, limit, name, address, phone, placementDate, status);
//            if (anotherGson.toJson(result).getCarts() != null)
//                return Response.ok().entity(anotherGson.toJson(result)).build();
//        } catch (Exception e) {
//            //
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
}