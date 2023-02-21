package com.stripe.lindqvistposters.stripe;

public class OrderDTO {

    public int orderposterId;

    public int orderquantity;


    public OrderDTO() {
    }

    public OrderDTO(int orderposterId, int orderquantity) {
        this.orderposterId = orderposterId;
        this.orderquantity = orderquantity;
    }

    //getters and setters

    public int getOrderposterId() {
        return orderposterId;
    }

    public void setOrderposterId(int orderposterId) {
        this.orderposterId = orderposterId;
    }

    public int getOrderquantity() {
        return orderquantity;
    }

    public void setOrderquantity(int orderquantity) {
        this.orderquantity = orderquantity;
    }




}
