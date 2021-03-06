package FurnitureStore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import FurnitureStore.dto.CartProductDTO;
import FurnitureStore.dto.OrderDTO;
import FurnitureStore.dto.ProductDTO;
import FurnitureStore.dto.UserDTO;
import FurnitureStore.dto.UserOrderDTO;

public class UserDAO extends AbstractDAO {
    public UserDAO() throws Exception {

    }

    public UserDAO(Connection conn) {
        super(conn);
    }

    public void writeUserDTO(UserDTO userDTO, ResultSet rs) throws Exception {
        userDTO.setId(rs.getInt("id"));
        userDTO.setUserName(rs.getString("UserName"));
        userDTO.setEmail(rs.getString("Email"));
        userDTO.setPassword(rs.getString("Password"));
        userDTO.setRole(rs.getString("Role"));
    }

    public void writeUserOrderDTO(UserOrderDTO userOrderDTO, ResultSet rs) throws Exception {
    	userOrderDTO.setQuantity(rs.getInt("quantity"));

        OrderDTO orderDTO = new OrderDTO();

        OrderDAO dummy = new OrderDAO();
        dummy.writeOrderDTO(orderDTO, rs);
        dummy.closeConnection();

        userOrderDTO.setOrder(orderDTO);
    }
    
    public UserDTO getUserFromName(String userName) throws Exception {
        UserDTO userDTO = new UserDTO();

        try {
            String query = "select * from Users where UserName=N'" + userName + "'";
            ResultSet rs = UserDAO.super.ExecuteQuery(query, null);
            if (rs.next()) {
                writeUserDTO(userDTO, rs);
            }
        } catch (Exception e) {
            // System.out.println(e.toString());
            e.printStackTrace();
        }
        return userDTO;
    }

    public UserDTO getUserFromEmail(String email) throws Exception {
        UserDTO userDTO = new UserDTO();

        try {
            String query = "select * from Users where Email=N'" + email + "'";
            ;
            ResultSet rs = UserDAO.super.ExecuteQuery(query, null);
            if (rs.next()) {
                writeUserDTO(userDTO, rs);
            }
        } catch (Exception e) {
            // System.out.println(e.toString());
            e.printStackTrace();
        }
        return userDTO;
    }
    
    
    //no proc
    public List<UserOrderDTO> getUserOrderFromID(Integer id) throws Exception {
    	 List<UserOrderDTO> orderList = new ArrayList<>();
         try {
             String orderListQuery = "EXEC USP_GetUserOrders ?";
             ResultSet orderRs = UserDAO.super.ExecuteQuery(orderListQuery, new Object[] { id });

             while (orderRs.next()) {
                 UserOrderDTO cartProductDTO = new UserOrderDTO();
                 writeUserOrderDTO(cartProductDTO, orderRs);

                 orderList.add(cartProductDTO);
             }
         } catch (Exception e) {
             e.printStackTrace();
             // throw e;
         }
         return orderList;
    }
    
    
    
    public boolean createUser(String userName, String email, String hashedPassword, String role) throws Exception {
        try {
            String query = "EXEC USP_InsertUser ? , ? , ? , ?";
            if (UserDAO.super.ExecuteNonQuery(query, new Object[] { userName, email, hashedPassword, role }) == 1)
                return true;
        } catch (Exception e) {
            // System.out.println(e.toString());
            e.printStackTrace();
        }
        return false;
    }

    public boolean setRole(String email, String role) throws Exception {
        try {
            String query = "EXEC USP_UpdateRole ? , ?";
            if (UserDAO.super.ExecuteNonQuery(query, new Object[] { email, role }) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String email) throws Exception {
        try {
            String query = "delete from Users where Email = N'" + email + "'";
            if (UserDAO.super.ExecuteNonQuery(query, null) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserDTO> getEmployees() throws Exception {
        List<UserDTO> employees = new ArrayList<>();
        try {
            String query = "select * from Users where Role != N'Admin'";
            ResultSet rs = UserDAO.super.ExecuteQuery(query, null);
            while (rs.next()) {
                UserDTO userDTO = new UserDTO();
                writeUserDTO(userDTO, rs);
                employees.add(userDTO);
            }
        } catch (Exception e) {
            // System.out.println(e.toString());
            e.printStackTrace();
        }
        return employees;
    }

    public boolean editUser(String email, String userName, String password) throws Exception {
        try {
            String query = "EXEC USP_UpdateUser ? , ? , ?";
            if (UserDAO.super.ExecuteNonQuery(query, new Object[] { userName, email, password }) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //no proc
    public boolean createUserOrder(Integer userID,Integer orderID,Integer quantity) throws Exception {
        try {
            boolean result = true;
           
                String productQuery = "EXEC USP_InsertUserOrder ? , ? , ?";
                if (UserDAO.super.ExecuteNonQuery(productQuery,
                        new Object[] { userID, orderID, quantity }) != 1)
                    result = false;
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
    //no proc yet
    public boolean removeUserOrder(Integer id,Integer orderID) throws Exception {
        try {
            String query = "EXEC USP_DeleteUserOrder ? , ? ";
            UserDAO.super.ExecuteNonQuery(query, new Object[] { id,orderID });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
}