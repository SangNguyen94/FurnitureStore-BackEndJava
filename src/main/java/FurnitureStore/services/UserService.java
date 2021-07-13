/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.services;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

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

import FurnitureStore.bo.CartBO;
import FurnitureStore.bo.UserBO;
import FurnitureStore.dto.UserDTO;
import FurnitureStore.dto.UserOrderDTO;
import FurnitureStore.helper.LocalDateTimeSerializer;
import FurnitureStore.helper.Authentication.AuthFilter;
import FurnitureStore.helper.Authentication.Role;

@Stateless
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({"application/json"})
public class UserService {

    public UserService() {
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
    public Response getCurrentUser() {
        try {
            UserDTO result = AuthFilter.currentUser;
            if (result != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

   
    @GET
    @Path("/getEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            UserBO userBO = new UserBO();
            List<UserDTO> result = userBO.getAllEmployees();
            if (!result.isEmpty())
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrder(@PathParam("id") Integer id) {
        try {
            UserBO userBO = new UserBO();
            List<UserOrderDTO> result = userBO.getUserOrders(id);
            if (!result.isEmpty())
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String entity) {
        try {
        	UserDTO userDetailDTO = anotherGson.fromJson(entity, UserDTO.class);
            UserBO userBO = new UserBO();
            UserDTO result = userBO.createUser(userDetailDTO.getUserName(), userDetailDTO.getEmail(), userDetailDTO.getPassword(),
            		userDetailDTO.getRole());
            if (result != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/login")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response login(String entity) {
        try {
        	UserDTO userDetailDTO = anotherGson.fromJson(entity, UserDTO.class);
            UserBO userBO = new UserBO();
            System.out.println("Api no problem");
            UserDTO result = userBO.checkPassAndEmail(userDetailDTO.getEmail(), userDetailDTO.getPassword());
            if (result != null) {
            	System.out.println("Check ok");
                return Response.ok().entity(anotherGson.toJson(result)).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @DELETE
    @Path("{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("email") String email) {
        try {
            UserBO userBO = new UserBO();
            if (userBO.deleteUser(email))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(String entity) {
        try {
            UserBO userBO = new UserBO();
        	Gson gson = new Gson() ;
            UserDTO userDetailDTO = gson.fromJson(entity, UserDTO.class);
            if (userBO.editUser(userDetailDTO.getEmail(), userDetailDTO.getUserName(), userDetailDTO.getPassword()))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    
    @PUT
    @Path("{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editRole(@PathParam("email") String email, @QueryParam("role") String role) {
        try {
            UserBO userBO = new UserBO();
            if (userBO.editRole(email, role)) {
                UserDTO result = userBO.getUserFromEmail(email);
                return Response.ok().entity(anotherGson.toJson(result)).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Path("{userID}/{orderID}/{quantity}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserOrder(@PathParam("userID") Integer userID,@PathParam("orderID") Integer orderID,@PathParam("quantity") Integer quantity) {
        try {
            UserBO userBO = new UserBO();
            if (userBO.createUserOrder(userID, orderID, quantity))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
//    @PUT
//    @Path("{cartID}/{productID}/{quantity}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateCartProduct(@PathParam("cartID") Integer cartID,@PathParam("productID") Integer productID,@PathParam("quantity") Integer quantity) {
//        try {
//            CartBO cartBO = new CartBO();
//            if (cartBO.updateCartedProduct(cartID, productID, quantity))
//                return Response.ok().build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
//        }
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }
    
    @DELETE
    @Path("{userID}/{orderID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUserOrder(@PathParam("userID") Integer userID,@PathParam("orderID") Integer orderID) {
        try {
            UserBO userBO = new UserBO();
           // System.out.println(cartID.toString()+" "+productID.toString());
            if (userBO.removeUserOrder(userID, orderID))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}