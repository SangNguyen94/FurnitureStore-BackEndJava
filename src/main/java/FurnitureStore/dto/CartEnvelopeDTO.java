package FurnitureStore.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CartEnvelopeDTO")

public class CartEnvelopeDTO {
    private List<CartDTO> carts;

    @XmlElement
    private Integer totalAmount;

	public CartEnvelopeDTO() {
		super();
	}

	public List<CartDTO> getCarts() {
		return carts;
	}

	public void setCarts(List<CartDTO> carts) {
		this.carts = carts;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

 

}