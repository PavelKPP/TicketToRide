package com.tickettoride.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


@Table(name = "user_table")
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "secondname")
    private String secondName;

    @Column(name = "travellerid",unique = true)
    private Long travellerId;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "email")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(Long travellerId) {
        this.travellerId = travellerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserModel user = (UserModel) object;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (secondName != null ? !secondName.equals(user.secondName) : user.secondName != null) return false;
        if (travellerId != null ? !travellerId.equals(user.travellerId) : user.travellerId != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        return emailAddress != null ? emailAddress.equals(user.emailAddress) : user.emailAddress  == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (travellerId != null ? travellerId.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result = 31 * result + (emailAddress != null ? balance.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", travellerId=" + travellerId +
                ", balance=" + balance +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
