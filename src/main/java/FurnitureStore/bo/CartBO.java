package FurnitureStore.bo;

import FurnitureStore.dao.CartDao;
import FurnitureStore.dao.ProductDAO;
import FurnitureStore.dto.CartDTO;
import FurnitureStore.dto.CartEnvelopeDTO;
import FurnitureStore.dto.CartProductDTO;
import FurnitureStore.dto.ProductDTO;

import java.sql.Date;
import java.util.List;

public class CartBO {

	public List<CartDTO> getAllCarts() throws Exception {
		CartDao cartDAO = null;
		PhotoBO photoBO = null;

		try {
			cartDAO = new CartDao();
			photoBO = new PhotoBO();

			List<CartDTO> result = cartDAO.getAll();
			for (CartDTO cartDTO : result) {
				List<CartProductDTO> productsList = cartDAO.getProductsInCart(cartDTO.getId());
				for (CartProductDTO productDTO : productsList) {
					productDTO.getProduct()
							.setImage(photoBO.getMainPhotoURL(productDTO.getProduct().getId().toString()));
				}
				cartDTO.setProducts(productsList);
			}

			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}

//    public CartEnvelopeDTO getFilteredCarts(int offset, int limit, String name, String address, String phone,
//            String date, String status) throws Exception {
//        CartDao cartDAO = null;
//        PhotoBO photoBO = null;
//        Date placementDate = null;
//        if (date != null)
//            placementDate = Date.valueOf(date);
//
//        try {
//            cartDAO = new CartDao();
//            photoBO = new PhotoBO();
//            CartEnvelopeDTO result = cartDAO.getFiltered(offset, limit, name, address, phone, placementDate,
//                    status);
//
//            // set products in orders
//            for (CartDTO cartDTO : result.getCarts()) {
//                List<CartProductDTO> productsList = cartDAO.getProductsInCart(cartDTO.getId());
//                for (CartProductDTO productDTO : productsList) {
//                    productDTO.getProduct()
//                            .setImage(photoBO.getMainPhotoURL(productDTO.getProduct().getId().toString()));
//                }
//                cartDTO.setProducts(productsList);
//            }
//            return result;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            cartDAO.closeConnection();
//        }
//    }

	public CartDTO getCartById(Integer id) throws Exception {
		CartDao cartDAO = null;
		PhotoBO photoBO = null;

		try {
			cartDAO = new CartDao();
			photoBO = new PhotoBO();
			List<CartProductDTO> productsList = cartDAO.getProductsInCart(id);
			for (CartProductDTO productDTO : productsList) {
				productDTO.getProduct().setImage(photoBO.getMainPhotoURL(productDTO.getProduct().getId().toString()));
			}

			CartDTO result = cartDAO.get(id);
			result.setProducts(productsList);

			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
	public CartDTO getCartByUserId(Integer id) throws Exception {
		CartDao cartDAO = null;
		PhotoBO photoBO = null;

		try {
			cartDAO = new CartDao();
			photoBO = new PhotoBO();
			List<CartProductDTO> productsList = cartDAO.getProductsInCart(id);
			for (CartProductDTO productDTO : productsList) {
				productDTO.getProduct().setImage(photoBO.getMainPhotoURL(productDTO.getProduct().getId().toString()));
			}

			CartDTO result = cartDAO.getByUserID(id);
			result.setProducts(productsList);

			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}


	public boolean createCart(CartDTO newCart) throws Exception {
		CartDao cartDAO = null;
		try {
			cartDAO = new CartDao();
			return cartDAO.create(newCart);
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
	
	public Integer cartCount(Integer cartID) throws Exception {
		CartDao cartDAO = null;
		try {
			cartDAO = new CartDao();
			return cartDAO.getCount(cartID);
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}

//	public boolean createCartedProduct(CartDTO newCart) throws Exception {
//		CartDao cartDAO = null;
//		try {
//			cartDAO = new CartDao();
//
//			return cartDAO.createCartProduct(newCart);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			cartDAO.closeConnection();
//		}
//	}

	public boolean editCart(Integer id, CartDTO cartDTO) throws Exception {
		CartDao cartDAO = null;
		try {
			cartDTO.setId(id);
			cartDAO = new CartDao();
			CartDTO result = cartDAO.get(id);
			if (result == null)
				return false;
			return cartDAO.edit(id, cartDTO);
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}

	public boolean createCartedProduct(Integer cartID, Integer productID, Integer quantity) throws Exception {
		CartDao cartDAO = null;

		try {

			cartDAO = new CartDao();

			CartDTO result = cartDAO.get(cartID);
			if (result == null)
				return false;

			if (cartDAO.createCartProduct(cartID, productID, quantity)) {
//                    if (result.getStatus().equals("Finished")) {
//                        if (!EditCartedProductQuantity(cartDTO, true))
//                            return false;
//                    }
				return true;
			}
//            } else {
//                if (cartDAO.removeCartProduct(id, id)) {
//                    if (cartDAO.createCartProduct(cartDTO)) {
////                        if ((result.getStatus().equals("Processing") && cartDTO.getStatus().equals("Finished"))) {
////                            if (!EditCartedProductQuantity(cartDTO, true))
////                                return false;
////                        }
//                        return true;
//                    }
//                }
			return false;

		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
	public boolean updateCartedProduct(Integer cartID, Integer productID, Integer quantity) throws Exception {
		CartDao cartDAO = null;

		try {

			cartDAO = new CartDao();

			CartDTO result = cartDAO.get(cartID);
			if (result == null)
				return false;
		
			if (cartDAO.updateCartProduct(cartID, productID, quantity)) {
//                    if (result.getStatus().equals("Finished")) {
//                        if (!EditCartedProductQuantity(cartDTO, true))
//                            return false;
//                    }
				return true;
			
			}
//            } else {
//                if (cartDAO.removeCartProduct(id, id)) {
//                    if (cartDAO.createCartProduct(cartDTO)) {
////                        if ((result.getStatus().equals("Processing") && cartDTO.getStatus().equals("Finished"))) {
////                            if (!EditCartedProductQuantity(cartDTO, true))
////                                return false;
////                        }
//                        return true;
//                    }
//                }
			return false;

		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
	public boolean removeCartedProduct(Integer cartID, Integer productID) throws Exception {
		CartDao cartDAO = null;

		try {

			cartDAO = new CartDao();

			CartDTO result = cartDAO.get(cartID);
			if (result == null)
				return false;

			if (cartDAO.removeCartProduct(cartID, productID)) {
//                    if (result.getStatus().equals("Finished")) {
//                        if (!EditCartedProductQuantity(cartDTO, true))
//                            return false;
//                    }
				return true;
			}

			return false;

		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
	

//    public boolean EditCartedProductQuantity(CartDTO cartDTO, boolean isPlus) throws Exception {
//        ProductDAO productDAO = null;
//        try {
//            productDAO = new ProductDAO();
//
//            for (CartProductDTO product : cartDTO.getProducts()) {
//                ProductDTO updateProduct = productDAO.get(product.getProduct().getId());
//                if (updateProduct == null)
//                    return false;
//
//                if (isPlus)
//                    updateProduct.setStock(updateProduct.getStock() + product.getQuantity());
//                else
//                    updateProduct.setStock(updateProduct.getStock() - product.getQuantity());
//
//                if (!productDAO.edit(updateProduct))
//                    return false;
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            productDAO.closeConnection();
//        }
//        return true;
//    }

	public boolean removeCart(Integer id) throws Exception {
		CartDao cartDAO = null;
		try {
			cartDAO = new CartDao();
			CartDTO result = cartDAO.get(id);
			if (result == null)
				return false;
			return cartDAO.remove(id);
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
	
	public boolean removeCartProductAll(Integer cartID) throws Exception {
		CartDao cartDAO = null;
		try {
			cartDAO = new CartDao();
			CartDTO result = cartDAO.get(cartID);
			if (result == null)
				return false;
			return cartDAO.DeleteAllCartProductByCart(cartID);
		} catch (Exception e) {
			throw e;
		} finally {
			cartDAO.closeConnection();
		}
	}
}
