package com.edushelf.payment.dto;

import com.edushelf.payment.entity.PaymentStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PaymentStatusUpdateRequest {
	@NotNull(message = "Payment reference cannot be null")
    @Pattern(regexp = "^[A-Z0-9]{8,20}$", message = "Payment reference must be alphanumeric and between 8-20 characters")
    private String paymentReference;

    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;

    // Getters and Setters
    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
