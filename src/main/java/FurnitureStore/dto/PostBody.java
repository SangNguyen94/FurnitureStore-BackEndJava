package FurnitureStore.dto;



import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;

@XmlRootElement(name = "PostBody")
public class PostBody {


	private List<Long> quantity;
   // private List<PriceData> priceData;
    private List<String> image;

	private List<String> currency;
	private List<String> productID;
	private List<Long> unitAmount;
    
    //products
   
	public List<Long> getQuantity() {
		return quantity;
	}



	public void setQuantity(List<Long> quantity) {
		this.quantity = quantity;
	}





	public List<String> getCurrency() {
		return currency;
	}



	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}



	public List<String> getProductID() {
		return productID;
	}



	public void setProductID(List<String> productID) {
		this.productID = productID;
	}



	public List<Long> getUnitAmount() {
		return unitAmount;
	}



	public void setUnitAmount(List<Long> unitAmount) {
		this.unitAmount = unitAmount;
	}



	public List<String> getImage() {
		return image;
	}



	public void setImage(List<String> image) {
		this.image = image;
	}



	public PostBody() {
		super();
	}

  
}