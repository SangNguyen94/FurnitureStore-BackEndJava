package FurnitureStore.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProductEnvelopeDTO")
public class ProductEnvelopeDTO {
  

	private List<ProductDTO> products;

    public ProductEnvelopeDTO() {
	}

	public ProductEnvelopeDTO(List<ProductDTO> products, Integer resultCount) {

		this.products = products;
		this.resultCount = resultCount;
	}

	@XmlElement
    private Integer resultCount;
    
    public Integer getResultCount() {
  		return resultCount;
  	}
    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

}