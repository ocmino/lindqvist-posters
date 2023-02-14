package com.stripe.lindqvistposters.stripe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /*@PostMapping
    public ResponseEntity<Object> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            invoiceService.createInvoice(
                    invoiceRequest.getCustomerName(),
                    invoiceRequest.getCustomerEmail(),
                    invoiceRequest.getCustomerAddress(),
                    invoiceRequest.getPostalCode(),
                    invoiceRequest.getCity(),
                    invoiceRequest.getPhone(),
                    invoiceRequest.getPosterId(),
                    invoiceRequest.getQuantity()
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    @PostMapping
    public ResponseEntity<Object> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            invoiceService.createInvoice(
                    invoiceRequest.getCustomerName(),
                    invoiceRequest.getCustomerEmail(),
                    invoiceRequest.getCustomerAddress(),
                    invoiceRequest.getPostalCode(),
                    invoiceRequest.getCity(),
                    invoiceRequest.getPhone(),
                    invoiceRequest.getPosterId(),
                    invoiceRequest.getQuantity()
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}






