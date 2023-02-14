package com.stripe.lindqvistposters.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.lindqvistposters.orders.Order;
import com.stripe.lindqvistposters.orders.OrderRepository;
import com.stripe.lindqvistposters.stripecustomers.StripeCustomer;
import com.stripe.lindqvistposters.stripecustomers.StripeCustomerRepository;

import com.stripe.lindqvistposters.stripeposters.StripePoster;
import com.stripe.lindqvistposters.stripeposters.StripePosterRepository;

import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.param.InvoiceItemCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoiceService {
    private static final String STRIPE_API_KEY = "sk_test_51MYrCsABdobpdja32NjYWCQdCpLZ4l00Qy2y4ltgmAehWLda4qNPghWgC0vr8ZbPFdvP7ZfT2mbvqkUIsgHmM2oS00VLJG5eHV";

    @Autowired
    private StripeCustomerRepository stripeCustomerRepository;

    @Autowired
    private StripePosterRepository stripePosterRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.api.key}")
    private String apiKey;

    public void createInvoice(String customerName, String customerEmail, String customerAddress, int postalCode, String city, int phone,
                              int posterId, int quantity) throws Exception {

        Stripe.apiKey = STRIPE_API_KEY;

        // Create a customer in Stripe
        Customer customer = createCustomerInStripe(customerName, customerEmail, customerAddress, postalCode, city, phone);

        // Save the customer information to the stripe customers table
        StripeCustomer stripeCustomer = new StripeCustomer(customer.getId(), customerName, customerEmail, customerAddress, postalCode, city, phone);
        stripeCustomerRepository.save(stripeCustomer);

        // Create an order in the orders table
        StripePoster stripePoster = stripePosterRepository.findById(posterId).orElse(null);
        if (stripePoster == null) {
            throw new Exception("Poster not found");
        }
        Order order = new Order(stripeCustomer.getId(), posterId, quantity, stripePoster.getPosterPrice(), new Date());
        orderRepository.save(order);

        // Get price id
        String priceId = stripePoster.getPriceId();
        System.out.println(priceId);

        // Get customer stripe id
        String customerId = stripeCustomer.getStripeId();
        System.out.println(customerId);

        // Create an Invoice
        InvoiceItemCreateParams invoiceItemParams =
                InvoiceItemCreateParams.builder()
                        .setCustomer(customerId)
                        .setPrice(priceId)
                        .setCurrency("sek")
                        .setQuantity((long) quantity)
                        .build();
        try {
            InvoiceItem.create(invoiceItemParams);
        } catch(StripeException e) {
            System.out.println("Error creating invoice item: " + e.getMessage());
        }

        Map<String, Object> invoiceParams = new HashMap<>();
        invoiceParams.put("customer", customer.getId());
        invoiceParams.put("auto_advance", true);
        invoiceParams.put("collection_method", "send_invoice");
        invoiceParams.put("days_until_due", 30);

        try {
            Invoice invoice = Invoice.create(invoiceParams);
            System.out.println("Invoice created: " + invoice.getId());
        } catch(StripeException e) {
            System.out.println("Error creating invoice: " + e.getMessage());
        }
    }


    private Customer createCustomerInStripe(String customerName, String customerEmail, String customerAddress, int postalCode, String city, int phone) throws Exception {
        try {
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("name", customerName);
            customerParams.put("email", customerEmail);
            customerParams.put("address", new HashMap<String, Object>() {{
                put("line1", customerAddress);
                put("postal_code", postalCode);
                put("city", city);
                put("state", "");
                put("country", "SE");
            }});
            customerParams.put("phone", phone);
            return Customer.create(customerParams);
        } catch (StripeException e) {
            throw new Exception("Error creating customer in Stripe: " + e.getMessage());
        }

    }

}