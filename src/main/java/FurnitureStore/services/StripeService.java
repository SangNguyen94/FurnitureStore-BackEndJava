package FurnitureStore.services;


import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.model.Price;
import com.stripe.exception.*;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

import FurnitureStore.bo.CartBO;
import FurnitureStore.bo.ProductBO;
import FurnitureStore.dto.CartDTO;
import FurnitureStore.dto.PostBody;
import FurnitureStore.dto.ProductDTO;
import FurnitureStore.dto.UserDTO;
import io.github.cdimascio.dotenv.Dotenv;

@Stateless
@Path("payment")
public class StripeService {
    private static Gson gson = new Gson();

//    static class PostBody {
//        @SerializedName("quantity")
//        Long quantity;
//        
//        public void setQuantity(long quantity)
//        {
//        	this.quantity=quantity;
//        }
//        
//        public Long getQuantity() {
//            return quantity;
//        }
//    }

    


//        staticFiles.externalLocation(
//                Paths.get(Paths.get("").toAbsolutePath().toString(), System.getenv("STATIC_DIR")).normalize().toString());

        
        // Fetch the Checkout Session to display the JSON result on the success page
       

       

      
    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfig() throws StripeException {
       

        Stripe.apiKey = "sk_test_51IrOE7IKHZePqhnOAJKub4w1t7lPuDK0uNYzVmcctHup9VxzlC7UHIX4RQtJOBSpyRIQdN1F7k6GS7c73PopxPdZ008zKoSnAL";
        Stripe.setAppInfo(
            "stripe-samples/checkout-one-time-payments",
            "0.0.1",
            "https://github.com/stripe-samples/checkout-one-time-payments"
        );

//
//        staticFiles.externalLocation(
//                Paths.get(Paths.get("").toAbsolutePath().toString(), System.getenv("STATIC_DIR")).normalize().toString());
////            spark.Response response = null;
//			response.type("application/json");
            Price price = Price.retrieve("price_1IrQwuIKHZePqhnOxm90ekE4");

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("publicKey", "pk_test_51IrOE7IKHZePqhnO0RSRGcGLWIEo1DJKFKLWhkyqaio52VqzPlqteYycIDtrL50q1qRAstObuZGMstGa4OULQdZQ00QB0MZHa1");
            responseData.put("unitAmount", price.getUnitAmount());
            responseData.put("currency", price.getCurrency());
         
            return  Response.ok().entity(new Gson().toJson(responseData)).build();
    }
    
  
    
    @GET
    @Path("/checkout-session")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCS(@QueryParam("sessionId") String sessionId) throws StripeException {
   
        Stripe.apiKey = "sk_test_51IrOE7IKHZePqhnOAJKub4w1t7lPuDK0uNYzVmcctHup9VxzlC7UHIX4RQtJOBSpyRIQdN1F7k6GS7c73PopxPdZ008zKoSnAL";
        Stripe.setAppInfo(
            "stripe-samples/checkout-one-time-payments",
            "0.0.1",
            "https://github.com/stripe-samples/checkout-one-time-payments"
        );


//        staticFiles.externalLocation(
//                Paths.get(Paths.get("").toAbsolutePath().toString(), System.getenv("STATIC_DIR")).normalize().toString());
////         
        
        
           

        
            Session session = Session.retrieve(sessionId);
            return  Response.ok().entity(new Gson().toJson(session)).build();
    }
    
    @POST
    @Path("/create-checkout-session")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCS(String jsonObject1) throws StripeException {
      
        Stripe.apiKey ="sk_test_51IrOE7IKHZePqhnOAJKub4w1t7lPuDK0uNYzVmcctHup9VxzlC7UHIX4RQtJOBSpyRIQdN1F7k6GS7c73PopxPdZ008zKoSnAL";
        Stripe.setAppInfo(
            "stripe-samples/checkout-one-time-payments",
            "0.0.1",
            "https://github.com/stripe-samples/checkout-one-time-payments"
        );

        Gson gson = new Gson();
        PostBody jsonObject = gson.fromJson(jsonObject1, PostBody.class);
//        staticFiles.externalLocation(
//                Paths.get(Paths.get("").toAbsolutePath().toString(), System.getenv("STATIC_DIR")).normalize().toString());
////            spark.Response response = null;
//			response.type("application/json");
           

            //PostBody postBody = gson.fromJson(jsonObject, PostBody.class);
            System.out.println(jsonObject.toString());
            String domainUrl = "http://furniture-store-sang.herokuapp.com";
            List<Long> quantity = jsonObject.getQuantity();
         //   List<PriceData> priceData= jsonObject.getPriceData();
            List<String> image=jsonObject.getImage();
            List<String> productID=jsonObject.getProductID();
            List<String> currency=jsonObject.getCurrency();
            List<Long> unitAmount = jsonObject.getUnitAmount();
            String price ="price_1IrQwuIKHZePqhnOxm90ekE4";
            
         
           
            
            System.out.println(image);
            // Pull the comma separated list of payment method types from the
            // environment variables stored in `.env`.  Then map to uppercase
            // strings so that we can lookup the PaymentMethodType enum values.
            //
            // In practice, you could hard code the list of strings representing
            // the payment method types you accept.
            String[] pmTypes = {"card"};
            List<PaymentMethodType> paymentMethodTypes = Stream
              .of(pmTypes)
              .map(String::toUpperCase)
              .map(PaymentMethodType::valueOf)
              .collect(Collectors.toList());

            // Create new Checkout Session for the order
            // Other optional params include:
            // [billing_address_collection] - to display billing address details on the page
            // [customer] - if you have an existing Stripe Customer ID
            // [customer_email] - lets you prefill the email input in the form
            // For full details see https://stripe.com/docs/api/checkout/sessions/create

            // ?session_id={CHECKOUT_SESSION_ID} means the redirect will have the session ID
            // set as a query param
            SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                    .setSuccessUrl(domainUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(domainUrl + "/canceled.html")
                    .addAllPaymentMethodType(paymentMethodTypes)
                    .setMode(SessionCreateParams.Mode.PAYMENT);

            // Add a line item for the sticker the Customer is purchasing
//            List<LineItem> items = new LineItem.Builder().setQuantity(quantity.get(1)).setPrice(price).build();
            
            List<LineItem> items = new ArrayList<LineItem>();
            for(int i=0;i<image.size();i++)
            {
            	Map<String, Object> productdata = new HashMap<>();
            	productdata.put("name", productID.get(i));
//            	productdata.put( "images", image.get(i));
            	 Map<String, Object> params = new HashMap<>();
                 params.put("unit_amount", unitAmount.get(i));
                 params.put("currency", currency.get(i));
                 params.put("product_data", productdata);

                 Price price1 = Price.create(params);
            	LineItem currentItem= new LineItem.Builder().setQuantity(quantity.get(i)).setPrice(price1.getId()).build();
            	items.add(currentItem);
            	
            }
            builder.addAllLineItem(items);
           // builder.addLineItem(item);

            SessionCreateParams createParams = builder.build();
            Session session = Session.create(createParams);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());

         
            return  Response.ok().entity(new Gson().toJson(responseData)).build();
    }
    
   
}
