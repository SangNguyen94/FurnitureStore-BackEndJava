package FurnitureStore.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CartDTO")
public class CartDTO {
    private Integer id;
    private Integer userID;
    private Integer totalAmount;
    
    //products
    private List<CartProductDTO> products;
	public CartDTO() {
		super();
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	

	

	public List<CartProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<CartProductDTO> products) {
		this.products = products;
	}

	
	public CartDTO(Integer id, Integer userID, Integer totalAmount, List<CartProductDTO> products) {
		super();
		this.id = id;
		this.userID = userID;
		this.totalAmount = totalAmount;
		this.products = products;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}


   

  
}