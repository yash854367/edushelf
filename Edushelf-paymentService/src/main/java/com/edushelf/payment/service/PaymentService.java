package com.edushelf.payment.service;

import com.edushelf.payment.dto.PaymentRequest;
import com.edushelf.payment.dto.PaymentResponse;
import com.edushelf.payment.dto.PaymentStatusUpdateRequest;
import com.edushelf.payment.entity.Payment;
import com.edushelf.payment.entity.PaymentStatus;
import com.edushelf.payment.exceptions.DuplicatePaymentException;
import com.edushelf.payment.exceptions.PaymentNotFoundException;
import com.edushelf.payment.exceptions.PaymentValidationException;
import com.edushelf.payment.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private String generatePaymentReference() {
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000); // Generate a random 5-digit number
        return "EDUSHELF" + randomNumber;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        // Check for duplicate payment key
        if (paymentRepository.findByKey(request.getKey()).isPresent()) {
            throw new DuplicatePaymentException("Payment with key '" + request.getKey() + "' already exists.");
        }

        if (request.getPaymentMethod() == null) {
            throw new PaymentValidationException("Invalid payment method.");
        }

        // Map DTO to entity
        Payment payment = new Payment();
        payment.setKey(request.getKey());
        payment.setCustomerId(request.getCustomerId());
        payment.setPaymentReference(generatePaymentReference());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPspName(request.getPspName());
        payment.setPaymentDate(LocalDateTime.now());

        // Simulate payment processing
        boolean isSuccessful = new Random().nextBoolean(); // Random success/failure
        payment.setPaymentStatus(isSuccessful ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

        // Save payment
        paymentRepository.save(payment);

        // Map to response
        return mapToResponse(payment);
    }

    public PaymentResponse updatePaymentStatus(PaymentStatusUpdateRequest request) {
        Payment payment = paymentRepository.findByPaymentReference(request.getPaymentReference())
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with reference: " + request.getPaymentReference()));

        payment.setPaymentStatus(request.getPaymentStatus());
        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    public PaymentResponse getPayment(String key) {
        Payment payment = paymentRepository.findByKey(key)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with key: " + key));

        return mapToResponse(payment);
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deletePayment(String key) {
        Payment payment = paymentRepository.findByKey(key)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with key: " + key));
        paymentRepository.delete(payment);
    }
    
    public PaymentStatus getPaymentStatus(String key) {
        Payment payment = paymentRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return payment.getPaymentStatus();
    }

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setKey(payment.getKey());
        response.setPaymentReference(payment.getPaymentReference());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        return response;
    }
}
