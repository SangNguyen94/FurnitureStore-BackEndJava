package FurnitureStore.bo;

import FurnitureStore.dao.ShipmentOptionsDAO;
import FurnitureStore.dto.ShipmentOptionsDTO;

public class ShipmentOptionsBO {
    public ShipmentOptionsDTO getShipmentOptions() throws Exception {
        ShipmentOptionsDAO shipmentOptionsDAO = null;
        try {
            shipmentOptionsDAO = new ShipmentOptionsDAO();
            return shipmentOptionsDAO.get();
        } catch (Exception e) {
            throw e;
        } finally {
            shipmentOptionsDAO.closeConnection();
        }

    }
}