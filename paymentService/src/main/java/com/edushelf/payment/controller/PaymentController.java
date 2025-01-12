package com.edushelf.payment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edushelf.payment.dto.PaymentRequest;
import com.edushelf.payment.dto.PaymentResponse;
import com.edushelf.payment.dto.PaymentStatusUpdateRequest;
import com.edushelf.payment.entity.PaymentStatus;
import com.edushelf.payment.exceptions.PaymentFailedException;
import com.edushelf.payment.service.PaymentService;

@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {


	private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{paymentReference}/status")
    public ResponseEntity<PaymentResponse> updateStatus(
            @PathVariable String paymentReference,
            @RequestBody @Valid PaymentStatusUpdateRequest request) {
        request.setPaymentReference(paymentReference);
        PaymentResponse response = paymentService.updatePaymentStatus(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{key}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String key) {
        PaymentResponse response = paymentService.getPayment(key);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);  
    }
    
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deletePayment(@PathVariable String key) {
        paymentService.deletePayment(key);
        return ResponseEntity.ok("Payment with key '" + key + "' has been deleted successfully.");
    }
    
    @GetMapping("/{key}/status")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable String key) {
        PaymentStatus status = paymentService.getPaymentStatus(key);
        return ResponseEntity.ok(status);
    }
}