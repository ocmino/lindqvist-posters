package com.stripe.lindqvistposters.stripeposters;

import jakarta.persistence.*;

@Entity
@Table(name = "stripeposters")
public class StripePoster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "priceid")
    private String priceId;

    @Column(name = "postername")
    private String posterName;

    @Column(name = "posterdescription")
    private String posterDescription;

    @Column(name = "posterprice")
    private int posterPrice;


    public StripePoster() {
    }

    public StripePoster(String priceId, String posterName, String posterDescription, int posterPrice) {
        this.priceId = priceId;
        this.posterName = posterName;
        this.posterDescription = posterDescription;
        this.posterPrice = posterPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterDescription() {
        return posterDescription;
    }

    public void setPosterDescription(String posterDescription) {
        this.posterDescription = posterDescription;
    }

    public int getPosterPrice() {
        return posterPrice;
    }

    public void setPosterPrice(int posterPrice) {
        this.posterPrice = posterPrice;
    }


}
