package com.stripe.lindqvistposters.stripecustomers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripeCustomerRepository extends JpaRepository<StripeCustomer, Integer> {

//get customer by stripeId
    StripeCustomer findByStripeId(String stripeId);

}
