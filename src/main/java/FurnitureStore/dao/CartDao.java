/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.dao;

import java.sql.Connection;
import	 java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import FurnitureStore.dto.CartDTO;
import FurnitureStore.dto.CartEnvelopeDTO;
import FurnitureStore.dto.CartProductDTO;
import FurnitureStore.dto.ProductDTO;
import FurnitureStore.dto.CartDTO;


/**
 *
 * @author Max
 */
public class CartDao extends AbstractDAO {

    public CartDao() throws Exception {

    }

    public CartDao(Connection conn) {
        super(conn);
    }

    public void writeCartDTO(CartDTO cartDTO, ResultSet rs) throws Exception {
        cartDTO.setId(rs.getInt("id"));
        cartDTO.setUserID(rs.getInt("fK_Cart_User"));
        cartDTO.setTotalAmount(rs.getInt("totalAmount"));
    }

    public void writeCartProductDTO(CartProductDTO cartProductDTO, ResultSet rs) throws Exception {
        cartProductDTO.setQuantity(rs.getInt("quantity"));

        ProductDTO productDTO = new ProductDTO();

        ProductDAO dummy = new ProductDAO();
        dummy.writeProductDTO(productDTO, rs);
        dummy.closeConnection();

        cartProductDTO.setProduct(productDTO);
    }

    public List<CartProductDTO> getProductsInCart(Integer id) throws Exception {
        List<CartProductDTO> productList = new ArrayList<>();
        try {
            String productListQuery = "EXEC USP_GetProductsInCart ?";
            ResultSet productListRs = CartDao.super.ExecuteQuery(productListQuery, new Object[] { id });

            while (productListRs.next()) {
                CartProductDTO cartProductDTO = new CartProductDTO();
                writeCartProductDTO(cartProductDTO, productListRs);

                productList.add(cartProductDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return productList;
    }
    public List<CartDTO> getAll() throws Exception {
        ArrayList<CartDTO> cartDTOList = new ArrayList<>();
        try {
            String query = "select * from Cart";
            ResultSet rs = CartDao.super.ExecuteQuery(query, null);
            while (rs.next()) {
                CartDTO cartDTO = new CartDTO();
                writeCartDTO(cartDTO, rs);
                cartDTOList.add(cartDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return cartDTOList;
    }
    
    public Integer getCount(Integer cartID) throws Exception {
        Integer returnCount = 0;
        try {
        	 String productListQuery = "EXEC USP_CountCart ?";
             ResultSet productListRs = CartDao.super.ExecuteQuery(productListQuery, new Object[] { cartID });
            while (productListRs.next()) {
              returnCount=productListRs.getInt(1);
              System.out.println(productListRs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return returnCount;
    }
//    public CartEnvelopeDTO getFiltered(int offset, int limit, String name, String brand, String category, int stock)
//            throws Exception {
//        CartEnvelopeDTO cartEnvelope = new CartEnvelopeDTO();
//        List<CartDTO> cartDTOList = new ArrayList<>();
//        try {
//            String query = "EXEC USP_FilterCart ? , ? , ? , ?";
//            ResultSet rs = CartDao.super.ExecuteQuery(query, new Object[] { name, brand, category, stock });
//            while (rs.next()) {
//                CartDTO cartDTO = new CartDTO();
//                writeCartDTO(cartDTO, rs);
//                cartDTOList.add(cartDTO);
//            }
//            cartEnvelope.setResultCount(cartDTOList.size());
//            if (limit != 0)
//                cartDTOList = cartDTOList.stream().skip(offset) // Equivalent to SQL's offset
//                        .limit(limit) // Equivalent to SQL's limit
//                        .collect(Collectors.toList());
//            cartEnvelope.setCarts(cartDTOList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // throw e;
//        }
//        return cartEnvelope;
//    }

    public CartDTO get(Integer id) throws Exception {
        CartDTO cartDTO = new CartDTO();
        try {
            String query = "select * from Cart where id=" + id;
            ResultSet rs = CartDao.super.ExecuteQuery(query, null);
            if (rs.next()) {
                writeCartDTO(cartDTO, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return cartDTO;
    }
    public CartDTO getByUserID(Integer id) throws Exception {
        CartDTO cartDTO = new CartDTO();
        try {
            String query = "select * from Cart where FK_Cart_User = " + id;
            ResultSet rs = CartDao.super.ExecuteQuery(query, null);
            if (rs.next()) {
                writeCartDTO(cartDTO, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return cartDTO;
    }
    
    public boolean DeleteAllCartProductByCart(Integer cartID)
    {
    	 try {
    		 System.out.println("at DAO");
    		 String query = "DELETE from Cart_Product where CartID=" + cartID;
             if (CartDao.super.ExecuteNonQuery(query,null) == 1)
                 return true;
         } catch (Exception e) {
             e.printStackTrace();
             // throw e;
         }
    	 return false;
    }

    public boolean create(CartDTO input) throws Exception {
        try {
            String query = "EXEC USP_InsertCart ? , ? ";
            if (CartDao.super.ExecuteNonQuery(query,
                    new Object[] { input.getUserID(), input.getTotalAmount() }) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }

    public boolean edit(Integer id,CartDTO input) throws Exception {
        try {
            String query = "EXEC USP_UpdateCartTotal ? , ? ";
            if (CartDao.super.ExecuteNonQuery(query, new Object[] { id,input.getTotalAmount() }) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }

    public boolean remove(Integer id) throws Exception {
        try {
            String query = "Delete from Cart where ID=" + id;
            if (CartDao.super.ExecuteNonQuery(query, null) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
    //no proc yet
    public boolean createCartProduct(Integer cartID,Integer productID,Integer quantity) throws Exception {
        try {
            boolean result = true;
           
                String productQuery = "EXEC USP_InsertCartProduct ? , ? , ?";
                if (CartDao.super.ExecuteNonQuery(productQuery,
                        new Object[] { cartID, productID, quantity }) != 1)
                    result = false;
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
    //no proc yet
    public boolean removeCartProduct(Integer id,Integer productID) throws Exception {
        try {
            String query = "EXEC USP_DeleteCartProduct ? , ? ";
            CartDao.super.ExecuteNonQuery(query, new Object[] { id,productID });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
    
    public boolean updateCartProduct(Integer cartID,Integer productID,Integer quantity) throws Exception {
        try {
            boolean result = true;
           
                String productQuery = "EXEC USP_UpdateCartProduct ? , ? , ?";
                if (CartDao.super.ExecuteNonQuery(productQuery,
                        new Object[] { cartID, productID, quantity }) != 1)
                    result = false;
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
}
