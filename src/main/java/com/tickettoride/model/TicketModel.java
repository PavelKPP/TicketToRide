package com.tickettoride.model;

import jakarta.persistence.*;
import org.apache.catalina.User;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ticket_table")
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "traveller_id")
    @Column(name = "travellerid", updatable = false)
    private Long travellers_id;


    @Column(name = "departurecity")
    private String departureCity;

    //The type of variable might be changed later
    @Column(name = "arrivalcity")
    private String arrivalCity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "segmentsquantity")
    private int segments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTravellers_id() {
        return travellers_id;
    }

    public void setTravellers_id(Long travellers_id) {
        this.travellers_id = travellers_id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TicketModel ticket = (TicketModel) object;

        if (id != null ? !id.equals(ticket.id) : ticket.id != null) return false;
        if (travellers_id != null ? !travellers_id.equals(ticket.travellers_id) : ticket.travellers_id != null)
            return false;
        if (departureCity != null ? !departureCity.equals(ticket.departureCity) : ticket.departureCity != null) return false;
        if (arrivalCity != null ? !arrivalCity.equals(ticket.arrivalCity) : ticket.arrivalCity != null) return false;
        return price != null ? price.equals(ticket.price) : ticket.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (travellers_id != null ? travellers_id.hashCode() : 0);
        result = 31 * result + (departureCity != null ? departureCity.hashCode() : 0);
        result = 31 * result + (arrivalCity != null ? arrivalCity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return  result;
    }

    @Override
    public String toString() {
        return "TicketModel{" +
                "id=" + id +
                ", travellers_id=" + travellers_id +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", price=" + price +
                ", segments=" + segments +
                '}';
    }
}
