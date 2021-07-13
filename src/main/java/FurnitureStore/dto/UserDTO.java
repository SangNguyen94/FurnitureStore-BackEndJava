package FurnitureStore.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserDTO")
public class UserDTO {
    @Override
	public String toString() {
		return "UserDTO []";
	}

	public UserDTO() {
	}

	public UserDTO(Integer id, String userName, String email, String password, String role, String token,
			List<UserOrderDTO> orders) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.token = token;
		this.orders = orders;
	}

	private Integer id;
    private String userName;
    private String email;
    private String password;
    private String role;
    private String token;

    private List<UserOrderDTO> orders;
    
    public List<UserOrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<UserOrderDTO> orders) {
		this.orders = orders;
	}

	public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}