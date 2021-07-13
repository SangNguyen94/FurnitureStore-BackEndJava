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

@XmlRootElement(name = "OrderShipmentDTO")
public class OrderShipmentDTO {
    private Integer id;
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDateTime deliverDate;
    private Integer orderID;
    private String shipmentStatus;
    private String shipmentCompany;
    private String shipmentID;

    public OrderShipmentDTO() {
    }

    public OrderShipmentDTO(final Integer id, final Integer orderID, final LocalDateTime deliverDate, final String shipmentID,
            final String shipmentCompany, final String shipmentStatus) {
        this.id = id;
        this.deliverDate = deliverDate;
        this.orderID = orderID;
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

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(final int orderID) {
        this.orderID = orderID;
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
