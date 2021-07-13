/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ImportShipmentDTO")
public class ImportShipmentDTO {
    private Integer id;
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDateTime deliverDate;
    private Integer importID;
    private String shipmentStatus;
    private String shipmentCompany;
    private String shipmentID;

    public ImportShipmentDTO() {
    }

    public ImportShipmentDTO(final Integer id, final Integer importID, final LocalDateTime deliverDate, final String shipmentID,
            final String shipmentCompany, final String shipmentStatus) {
        this.id = id;
        this.deliverDate = deliverDate;
        this.importID = importID;
        this.shipmentStatus = shipmentStatus;
        this.shipmentCompany = shipmentCompany;
        this.shipmentID = shipmentID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public LocalDateTime getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(final LocalDateTime deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(final String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Integer getImportID() {
        return importID;
    }

    public void setImportID(final int ImportID) {
        this.importID = ImportID;
    }

    public String getShipmentCompany() {
        return shipmentCompany;
    }

    public void setShipmentCompany(final String shipmentCompany) {
        this.shipmentCompany = shipmentCompany;
    }

    public String getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(final String shipmentID) {
        this.shipmentID = shipmentID;
    }

}
