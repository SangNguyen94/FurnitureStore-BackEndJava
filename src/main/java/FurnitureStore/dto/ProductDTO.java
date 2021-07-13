/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ProductDTO")
public class ProductDTO {
    private Integer id;
    private String name;
    private String brand;
    private String category;
    private String description;
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private long price;
    private long importPrice;
    private int stock;
   @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDateTime dateAdded;

    // Photo
    private String image;
    private List<PhotoDTO> photos;

    public ProductDTO() {
    	super();
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductDTO(final int id, final String name, final String brand, final String category,final String description, final long price,
            final long importPrice, final int stock, final LocalDateTime dateAdded) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description=description;
        this.category = category;
        this.price = price;
        this.importPrice = importPrice;
        this.stock = stock;
        this.dateAdded = dateAdded;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(final long price) {
        this.price = price;
    }

    public long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(final long importPrice) {
        this.importPrice = importPrice;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

	public ProductDTO(String name, String brand, String category, long price, long importPrice, int stock,
			LocalDateTime dateAdded, String image, List<PhotoDTO> photos) {
		super();
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.price = price;
		this.description=description;
		this.importPrice = importPrice;
		this.stock = stock;
		this.dateAdded = dateAdded;
		this.image = image;
		this.photos = photos;
	}
    
}
