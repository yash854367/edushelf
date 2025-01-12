package com.edushelf.payment.dto;

import javax.validation.constraints.NotNull;

import com.edushelf.payment.entity.PaymentMethodType;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PaymentRequest {

	@NotNull(message = "Payment key cannot be null")
    @Size(min = 5, max = 50, message = "Payment key must be between 5 and 50 characters")
	@Column(unique = true)
    private String key;

    @NotNull(message = "Customer ID cannot be null")
    @Min(value = 1, message = "Customer ID must be a positive number")
    private Long customerId;

    @NotNull(message = "Payment reference cannot be null")
    @Column(unique = true)
    @Pattern(regexp = "^[A-Z0-9]{8,20}$", message = "Payment reference must be alphanumeric and between 8-20 characters")
    private String paymentReference;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "1.00", message = "Amount must be greater than 1")
    private Double amount;

    @NotNull(message = "Payment method cannot be null")
    private PaymentMethodType paymentMethod;

    @NotNull(message = "PSP name cannot be null")
    @Size(min = 2, max = 50, message = "PSP name must be between 2 and 50 characters")
    private String pspName;
    public PaymentRequest() {}
    public PaymentRequest(String key, Long customerId, String paymentReference, Double amount, PaymentMethodType paymentMethod, String pspName) {
        this.key = key;
        this.customerId = customerId;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.pspName = pspName;
    }
    // Getters and Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPspName() {
        return pspName;
    }

    public void setPspName(String pspName) {
        this.pspName = pspName;
    }
}