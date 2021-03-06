/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import FurnitureStore.dto.ProductDTO;
import FurnitureStore.dto.ProductEnvelopeDTO;

/**
 *
 * @author Max
 */
public class ProductDAO extends AbstractDAO {

    public ProductDAO() throws Exception {

    }

    public ProductDAO(Connection conn) {
        super(conn);
    }

    public void writeProductDTO(ProductDTO productDTO, ResultSet rs) throws Exception {
        productDTO.setId(rs.getInt("id"));
        productDTO.setName(rs.getString("name"));
        productDTO.setBrand(rs.getString("brand"));
        productDTO.setCategory(rs.getString("category"));
        productDTO.setDescription(rs.getString("description"));
        productDTO.setPrice(rs.getLong("price"));
        productDTO.setImportPrice(rs.getLong("importPrice"));
        productDTO.setStock(rs.getInt("stock"));
        productDTO.setDateAdded(rs.getTimestamp("dateAdded").toLocalDateTime());
    }

    public List<ProductDTO> getAll() throws Exception {
        ArrayList<ProductDTO> productDTOList = new ArrayList<>();
        try {
            String query = "select * from Product";
            ResultSet rs = ProductDAO.super.ExecuteQuery(query, null);
            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO();
                writeProductDTO(productDTO, rs);
                productDTOList.add(productDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return productDTOList;
    }

    public ProductEnvelopeDTO getFiltered(int offset, int limit, String name, String brand, String category, int stock)
            throws Exception {
        ProductEnvelopeDTO productEnvelope = new ProductEnvelopeDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();
        System.out.println("current limit: "+limit);
        try {
            String query = "EXEC USP_FilterProduct ? , ? , ? , ?";
            ResultSet rs = ProductDAO.super.ExecuteQuery(query, new Object[] { name, brand, category, stock });
            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO();
                writeProductDTO(productDTO, rs);
                productDTOList.add(productDTO);
            }
            productEnvelope.setResultCount(productDTOList.size());
            System.out.println("result Count: "+productDTOList.size());
          
            if (limit != 0)
                productDTOList = productDTOList.stream().skip(offset) // Equivalent to SQL's offset
                        .limit(limit) // Equivalent to SQL's limit
                        .collect(Collectors.toList());
            productEnvelope.setProducts(productDTOList);
           
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        System.out.println(productEnvelope.getResultCount());
        return productEnvelope;
    }

    public ProductDTO get(Integer id) throws Exception {
        ProductDTO productDTO = new ProductDTO();
        try {
            String query = "select * from Product where id=" + id;
            ResultSet rs = ProductDAO.super.ExecuteQuery(query, null);
            if (rs.next()) {
                writeProductDTO(productDTO, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return productDTO;
    }

    public boolean create(ProductDTO input) throws Exception {
        try {
            String query = "EXEC USP_InsertProduct ? , ? , ? , ? , ? , ? , ? , ?";
            if (ProductDAO.super.ExecuteNonQuery(query,
                    new Object[] { input.getName(), input.getBrand(), input.getCategory(),input.getDescription(), input.getPrice(),
                            input.getImportPrice(), input.getStock(), input.getDateAdded() }) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }

    public boolean edit(ProductDTO input) throws Exception {
        try {
            String query = "EXEC USP_UpdateProduct ? , ? , ? , ? , ? , ? , ? , ? ,?";
            if (ProductDAO.super.ExecuteNonQuery(query,
                    new Object[] { input.getId(), input.getName(), input.getBrand(), input.getCategory(),input.getDescription(),
                            input.getPrice(), input.getImportPrice(), input.getStock(), input.getDateAdded() }) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }

    public boolean remove(Integer id) throws Exception {
        try {
            String query = "Delete from Product where Id=" + id;
            if (ProductDAO.super.ExecuteNonQuery(query, null) == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }
        return false;
    }
}
