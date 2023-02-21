package com.stripe.lindqvistposters.stripe;

import java.util.List;

public class InvoiceRequest {
    private String customerName;

    private String customerEmail;
    private String customerAddress;
    private int postalCode;
    private String city;
    private int phone;

    private List<Integer> posterIds;


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

    public List<Integer> getPosterIds() {
        return posterIds;
    }

    public void setPosterIds(List<Integer> posterIds) {
        this.posterIds = posterIds;
    }


}
