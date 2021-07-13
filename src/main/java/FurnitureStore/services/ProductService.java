/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * REST Web Service
 *
 * @author Max
 */

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

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import FurnitureStore.bo.ProductBO;
import FurnitureStore.dto.ProductDTO;
import FurnitureStore.dto.ProductEnvelopeDTO;
import FurnitureStore.dto.UserDTO;
import FurnitureStore.helper.LocalDateTimeSerializer;
import FurnitureStore.helper.Authentication.Role;
import FurnitureStore.helper.IntegerTypeAdapter;
@Stateless
@Path("products")
@PermitAll
public class ProductService {

    public ProductService() {
    }
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() { 
		@Override
		public LocalDate deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws com.google.gson.JsonParseException {
			// TODO Auto-generated method stub
			return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); }
    	}).create();

    	
 Gson anotherGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        
		@Override
		public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws com.google.gson.JsonParseException {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
		
		}
    }).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).registerTypeAdapter(Integer.class,new IntegerTypeAdapter()).create();

    @POST
    @Consumes({"application/json"})
    public Response create(String entity) {
        try {
//        	Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
//        	JsonParser jp = new JsonParser();
//        	JsonElement je = jp.parse(entity);
//        	String prettyJsonString = gson1.toJson(je);
//        	System.out.println(prettyJsonString);
        	//Gson gson = new Gson();
        	ProductDTO productDTO = anotherGson.fromJson(entity, ProductDTO.class);
            ProductBO productBO = new ProductBO();
            if (productBO.createProduct(productDTO))
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
            ProductBO productBO = new ProductBO();
        	ProductDTO productDTO = anotherGson.fromJson(entity, ProductDTO.class);
            if (productBO.editProduct(id, productDTO))
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
            ProductBO productBO = new ProductBO();
            if (productBO.removeProduct(id))
                return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // @RolesAllowed({ Role.ROLE_ADMIN, Role.ROLE_EMPLOYEE })
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
    	ObjectMapper mapper = JsonMapper.builder()
    		    .addModule(new JavaTimeModule())
    		    .build();
        ProductBO productBO = new ProductBO();
        ProductDTO result = new ProductDTO();
        try {
            result = productBO.getProductById(id);
            if (result.getName() != null)
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
    public Response filterProduct(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
            @QueryParam("name") String name, @QueryParam("brand") String brand, @QueryParam("category") String category,
            @QueryParam("stock") int stock) {
        ProductBO productBO = new ProductBO();
        ObjectMapper mapper = JsonMapper.builder()
    		    .addModule(new JavaTimeModule())
    		    .build();
        ProductEnvelopeDTO result = new ProductEnvelopeDTO();
        try {
            result = productBO.getFilteredProducts(offset, limit, name, brand, category, stock);
            if (result.getProducts() != null)
                return Response.ok().entity(anotherGson.toJson(result)).build();
        } catch (Exception e) {
            //
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}