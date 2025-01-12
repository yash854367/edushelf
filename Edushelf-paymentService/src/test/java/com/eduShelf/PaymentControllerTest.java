package com.eduShelf;

import com.edushelf.payment.controller.PaymentController;
import com.edushelf.payment.dto.PaymentRequest;
import com.edushelf.payment.dto.PaymentResponse;
import com.edushelf.payment.dto.PaymentStatusUpdateRequest;
import com.edushelf.payment.entity.PaymentMethodType;
import com.edushelf.payment.entity.PaymentStatus;
import com.edushelf.payment.service.PaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPayment_shouldReturnCreatedPayment() {
        PaymentRequest request = new PaymentRequest(
                "PAY12345",
                1L,
                "PAYREF12345678",
                100.50,
                PaymentMethodType.CREDIT_CARD,
                "Stripe"
        );
        PaymentResponse response = new PaymentResponse();
        when(paymentService.processPayment(request)).thenReturn(response);

        ResponseEntity<PaymentResponse> result = paymentController.createPayment(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(paymentService, times(1)).processPayment(request);
    }

    @Test
    void updateStatus_shouldReturnUpdatedPayment() {
        String paymentReference = "EDUSHELF55012";

        PaymentStatusUpdateRequest request = new PaymentStatusUpdateRequest();
        request.setPaymentReference(paymentReference);

        PaymentResponse response = new PaymentResponse();
        when(paymentService.updatePaymentStatus(request)).thenReturn(response);

        ResponseEntity<PaymentResponse> result = paymentController.updateStatus(paymentReference, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(paymentService, times(1)).updatePaymentStatus(request);
    }

    @Test
    void getPayment_shouldReturnPayment() {
        String key = "PAY12345";
        PaymentResponse response = new PaymentResponse();
        when(paymentService.getPayment(key)).thenReturn(response);

        ResponseEntity<PaymentResponse> result = paymentController.getPayment(key);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(paymentService, times(1)).getPayment(key);
    }

    @Test
    void getAllPayments_shouldReturnListOfPayments() {
        List<PaymentResponse> responses = Arrays.asList(new PaymentResponse(), new PaymentResponse());
        when(paymentService.getAllPayments()).thenReturn(responses);

        ResponseEntity<List<PaymentResponse>> result = paymentController.getAllPayments();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void deletePayment_shouldDeletePaymentSuccessfully() {
        String key = "PAY12345";

        ResponseEntity<String> result = paymentController.deletePayment(key);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Payment with key '" + key + "' has been deleted successfully.", result.getBody());
        verify(paymentService, times(1)).deletePayment(key);
    }

    @Test
    void getPaymentStatus_shouldReturnPaymentStatus() {
        String key = "PAY12345";
        PaymentStatus status = PaymentStatus.SUCCESS;
        when(paymentService.getPaymentStatus(key)).thenReturn(status);

        ResponseEntity<PaymentStatus> result = paymentController.getPaymentStatus(key);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(status, result.getBody());
        verify(paymentService, times(1)).getPaymentStatus(key);
    }
}
