package FurnitureStore.dto;

import javax.xml.bind.annotation.XmlRootElement;


public class UserOrderDTO {
	private OrderDTO order;
    private int quantity;
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

  
}