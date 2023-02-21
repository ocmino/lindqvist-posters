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
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.InvoiceItemCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

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

    public void createInvoice(String customerName, String customerEmail, String customerAddress, int postalCode, String city, int phone, List<Integer> posterIds) throws Exception {
        Stripe.apiKey = STRIPE_API_KEY;

        // Create a customer in Stripe
        Customer customer = createCustomerInStripe(customerName, customerEmail, customerAddress, postalCode, city, phone);

        // Save the customer information to the stripe customers table
        StripeCustomer stripeCustomer = new StripeCustomer(customer.getId(), customerName, customerEmail, customerAddress, postalCode, city, phone);
        stripeCustomerRepository.save(stripeCustomer);

        List<OrderDTO> orderDTOs = new ArrayList<>();

        // Calculate total amount
        double totalAmount = 0.0;
        for (int i = 0; i < posterIds.size(); i++) {
            StripePoster stripePoster = stripePosterRepository.findById(posterIds.get(i)).orElse(null);
            if (stripePoster == null) {
                throw new Exception("Poster not found");
            }
            // Check if poster exists in orderDTOs
            boolean posterExists = false;
            for (OrderDTO orderDTO : orderDTOs) {
                if (orderDTO.getOrderposterId() == stripePoster.getId()) {
                    orderDTO.setOrderquantity(orderDTO.getOrderquantity() + 1);
                    posterExists = true;
                }
            }
            if (!posterExists) {
                OrderDTO orderDTO1 = new OrderDTO(stripePoster.getId(), 1);
                orderDTOs.add(orderDTO1);
            }
            totalAmount += stripePoster.getPosterPrice();
        }

        // Get customer stripe id
        String customerId = stripeCustomer.getStripeId();

        for (OrderDTO orderDTO : orderDTOs) {
            // Get price id
            StripePoster stripePoster = stripePosterRepository.findById(orderDTO.getOrderposterId()).orElse(null);
            if (stripePoster == null) {
                throw new Exception("Poster not found");
            }
            String priceId = stripePoster.getPriceId();

            // Create an Invoice Item
            InvoiceItemCreateParams invoiceItemParams = InvoiceItemCreateParams.builder()
                    .setCustomer(customerId)
                    .setPrice(priceId)
                    .setCurrency("sek")
                    .setQuantity((long) orderDTO.getOrderquantity())
                    .build();
            try {
                InvoiceItem invoiceItem = InvoiceItem.create(invoiceItemParams);
                System.out.println("Invoice item created: " + invoiceItem.getId());
            } catch (StripeException e) {
                System.out.println("Error creating invoice item: " + e.getMessage());
            }
        }

        // Create an Invoice
        InvoiceCreateParams invoiceParams = InvoiceCreateParams.builder()
                .setCustomer(customer.getId())
                .setAutoAdvance(true)
                .setCollectionMethod(InvoiceCreateParams.CollectionMethod.SEND_INVOICE)
                .setDaysUntilDue(30L)
                .build();

        try {
            Invoice invoice = Invoice.create(invoiceParams);
            System.out.println("Invoice created: " + invoice.getId());
        } catch (StripeException e) {
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