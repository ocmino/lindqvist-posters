package com.stripe.lindqvistposters.stripeposters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripePosterRepository extends JpaRepository<StripePoster, Integer> {

    //find priceId which is a string
    StripePoster findByPriceId(String priceId);
}