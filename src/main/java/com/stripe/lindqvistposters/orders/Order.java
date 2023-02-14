package com.stripe.lindqvistposters.orders;

import com.stripe.lindqvistposters.stripecustomers.StripeCustomer;
import com.stripe.lindqvistposters.stripeposters.StripePoster;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid", referencedColumnName = "id")
    private StripeCustomer stripeCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posterid", referencedColumnName = "id")
    private StripePoster stripePoster;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "totalprice")
    private Integer totalPrice;

    @Column(name = "orderdate")
    private Timestamp orderDate;

    public Order() {
    }

    public Order(StripeCustomer stripeCustomer, StripePoster stripePoster, Integer quantity, Integer totalPrice, Timestamp orderDate) {
        this.stripeCustomer = stripeCustomer;
        this.stripePoster = stripePoster;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Order(int id, int posterId, int quantity, int totalPrice, Date date) {

    }

    public Order(String customerId, int posterId, int quantity, int i, Date date) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StripeCustomer getStripeCustomer() {
        return stripeCustomer;
    }

    public void setStripeCustomer(StripeCustomer stripeCustomer) {
        this.stripeCustomer = stripeCustomer;
    }

    public StripePoster getStripePoster() {
        return stripePoster;
    }

    public void setStripePoster(StripePoster stripePoster) {
        this.stripePoster = stripePoster;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
}
