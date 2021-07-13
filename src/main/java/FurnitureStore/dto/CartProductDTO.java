package FurnitureStore.dto;

import javax.xml.bind.annotation.XmlRootElement;


public class CartProductDTO {
	private ProductDTO product;
    private int quantity;
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public CartProductDTO() {
		super();
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

  
}