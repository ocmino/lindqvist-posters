package com.stripe.lindqvistposters.stripecustomers;

import jakarta.persistence.*;

@Entity
@Table(name = "stripecustomers")
public class StripeCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "stripeid")
    private String stripeId;

    @Column(name = "customername")
    private String customerName;

    @Column(name = "customeremail")
    private String customerEmail;

    @Column(name = "customeraddress")
    private String customerAddress;

    @Column(name = "postalcode")
    private int postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private int phone;


    public StripeCustomer() {
    }

    public StripeCustomer(String stripeId, String customerName, String customerEmail, String customerAddress, int postalCode, String city, int phone) {
        this.stripeId = stripeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }




}
