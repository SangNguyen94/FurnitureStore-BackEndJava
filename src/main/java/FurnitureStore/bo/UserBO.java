package FurnitureStore.bo;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import FurnitureStore.dao.CartDao;
import FurnitureStore.dao.UserDAO;
import FurnitureStore.dto.CartDTO;
import FurnitureStore.dto.CartProductDTO;
import FurnitureStore.dto.UserDTO;
import FurnitureStore.dto.UserOrderDTO;
import FurnitureStore.helper.Authentication.*;

public class UserBO {

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            return true;
        return false;
    }

    public UserDTO checkPassAndEmail(String email, String plainPassword) throws Exception {
        UserDAO userDAO = null;
        //JwtGenerator generator = null;

        try {
            userDAO = new UserDAO();
           // generator = new JwtGenerator();

            UserDTO userDTO = userDAO.getUserFromEmail(email);
            
            if (userDTO != null) {
//                if (token != null) {
//                    if (generator.decodeJWT(token, email)) {
//                        userDTO.setToken(token);
//                        return userDTO;
//                    }
                if (checkPass(plainPassword, userDTO.getPassword())) {
                	{System.out.println("Check successful");
                	List<UserOrderDTO> orderList = userDAO.getUserOrderFromID(userDTO.getId());		
        			userDTO.setOrders(orderList);
                    //userDTO.setToken(generator.createJWT(userDTO));
                    return userDTO;
                	}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return null;
    }

    public List<UserOrderDTO> getUserOrders(Integer id) throws Exception {
        UserDAO userDAO = null;
        //JwtGenerator generator = null;

        try {
            userDAO = new UserDAO();
           // generator = new JwtGenerator();

            List<UserOrderDTO> orderList = userDAO.getUserOrderFromID(id);
			return orderList;
            
           
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return null;
    }
    
    public UserDTO createUser(String userName, String email, String plainPassword, String role) throws Exception {
        UserDAO userDAO = null;
        // JwtGenerator generator = null;

        try {
            userDAO = new UserDAO();
            // generator = new JwtGenerator();
            UserDTO userDTO = userDAO.getUserFromEmail(email);
            if (userDTO.getUserName() == null || userDTO.getUserName().isEmpty()) {
                if (userDAO.createUser(userName, email, hashPassword(plainPassword), role)) {
                    UserDTO result = userDAO.getUserFromEmail(email);
                    if (result != null) {
                        // result.setToken(generator.createJWT(result));
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return null;
    }

    public boolean setUserMain(String email, String role) throws Exception {
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();
            UserDTO userDTO = userDAO.getUserFromEmail(email);
            if (userDTO.getUserName() != null || !userDTO.getUserName().isEmpty()) {
                return userDAO.setRole(email, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return false;
    }

    public boolean deleteUser(String email) throws Exception {
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();
            UserDTO userDTO = userDAO.getUserFromEmail(email);

            if (userDTO.getUserName() != null || !userDTO.getUserName().isEmpty() && userDTO.getRole() != "Admin") {
                return userDAO.deleteUser(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return false;
    }

    public List<UserDTO> getAllEmployees() throws Exception {
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();

            List<UserDTO> result = userDAO.getEmployees();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            userDAO.closeConnection();
        }
    }

    public UserDTO getUserFromEmail(String email) throws Exception {
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();

            UserDTO result = userDAO.getUserFromEmail(email);
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            userDAO.closeConnection();
        }
    }

    public boolean editUser(String email, String userName, String password) throws Exception {
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();
            UserDTO userDTO = userDAO.getUserFromEmail(email);

            // if password is the same skip password update
            String passwordParam = "";
            if (!checkPass(password, userDTO.getPassword()))
                passwordParam = hashPassword(password);
            if (userDTO.getUserName() != null || !userDTO.getUserName().isEmpty()) {
                if (userDAO.editUser(email, userName, passwordParam)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return false;
    }

    public boolean editRole(String email, String role) throws Exception {
        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();
            UserDTO userDTO = userDAO.getUserFromEmail(email);

            if (userDTO.getUserName() != null || !userDTO.getUserName().isEmpty()) {
                if (userDAO.setRole(email, role)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.closeConnection();
        }
        return false;
    }
    
    public boolean createUserOrder(Integer userID, Integer orderID, Integer quantity) throws Exception {
		UserDAO userDAO = null;

		try {

			userDAO = new UserDAO();

//			UserDTO result = UserDAO.get(userID);
//			if (result == null)
//				return false;

			if (userDAO.createUserOrder(userID, orderID, quantity)) {

				return true;
			}

			return false;

		} catch (Exception e) {
			throw e;
		} finally {
			userDAO.closeConnection();
		}
	}
    
    public boolean removeUserOrder(Integer userID, Integer orderID) throws Exception {
		UserDAO userDAO = null;

		try {

			userDAO = new UserDAO();

//			UserDTO result = UserDAO.get(userID);
//			if (result == null)
//				return false;

			if (userDAO.removeUserOrder(userID, orderID)) {

				return true;
			}

			return false;

		} catch (Exception e) {
			throw e;
		} finally {
			userDAO.closeConnection();
		}
	}
}