package com.eduShelf;

import com.edushelf.payment.dto.PaymentRequest;
import com.edushelf.payment.dto.PaymentResponse;
import com.edushelf.payment.dto.PaymentStatusUpdateRequest;
import com.edushelf.payment.entity.Payment;
import com.edushelf.payment.entity.PaymentMethodType;
import com.edushelf.payment.entity.PaymentStatus;
import com.edushelf.payment.exceptions.DuplicatePaymentException;
import com.edushelf.payment.exceptions.PaymentNotFoundException;
import com.edushelf.payment.exceptions.PaymentValidationException;
import com.edushelf.payment.repository.PaymentRepository;
import com.edushelf.payment.service.PaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

	@Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for processPayment()
    @Test
    void processPayment_shouldSaveAndReturnResponse() {
        PaymentRequest request = new PaymentRequest();
        request.setKey("unique-key");
        request.setCustomerId(1L);
        request.setAmount(100.0);
        request.setPaymentMethod(PaymentMethodType.CREDIT_CARD);
        request.setPspName("Test PSP");

        Payment payment = new Payment();
        payment.setKey(request.getKey());
        payment.setPaymentReference("EDUSHELF12345");
        payment.setAmount(request.getAmount());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        when(paymentRepository.findByKey(request.getKey())).thenReturn(Optional.empty());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse response = paymentService.processPayment(request);

        assertNotNull(response);
        assertEquals(PaymentStatus.SUCCESS, response.getPaymentStatus());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void processPayment_shouldThrowDuplicatePaymentException() {
        PaymentRequest request = new PaymentRequest();
        request.setKey("duplicate-key");

        when(paymentRepository.findByKey(request.getKey())).thenReturn(Optional.of(new Payment()));

        assertThrows(DuplicatePaymentException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void processPayment_shouldThrowPaymentValidationExceptionForNullPaymentMethod() {
        PaymentRequest request = new PaymentRequest();
        request.setKey("unique-key");
        request.setCustomerId(1L);
        request.setAmount(100.0);
        request.setPaymentMethod(null);

        assertThrows(PaymentValidationException.class, () -> paymentService.processPayment(request));
    }

    // Test for updatePaymentStatus()
    @Test
    void updatePaymentStatus_shouldUpdatePaymentAndReturnResponse() {
        PaymentStatusUpdateRequest request = new PaymentStatusUpdateRequest();
        request.setPaymentReference("EDUSHELF12345");
        request.setPaymentStatus(PaymentStatus.SUCCESS);

        Payment payment = new Payment();
        payment.setPaymentReference(request.getPaymentReference());

        when(paymentRepository.findByPaymentReference(request.getPaymentReference())).thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.updatePaymentStatus(request);

        assertNotNull(response);
        assertEquals(PaymentStatus.SUCCESS, response.getPaymentStatus());
        verify(paymentRepository).save(payment);
    }

    @Test
    void updatePaymentStatus_shouldThrowPaymentNotFoundException() {
        PaymentStatusUpdateRequest request = new PaymentStatusUpdateRequest();
        request.setPaymentReference("INVALID_REFERENCE");

        when(paymentRepository.findByPaymentReference(request.getPaymentReference())).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.updatePaymentStatus(request));
    }

    // Test for getPayment()
    @Test
    void getPayment_shouldReturnPaymentResponse() {
        Payment payment = new Payment();
        payment.setKey("unique-key");

        when(paymentRepository.findByKey("unique-key")).thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getPayment("unique-key");

        assertNotNull(response);
        assertEquals("unique-key", response.getKey());
        verify(paymentRepository).findByKey("unique-key");
    }

    @Test
    void getPayment_shouldThrowPaymentNotFoundException() {
        when(paymentRepository.findByKey("INVALID_KEY")).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.getPayment("INVALID_KEY"));
    }

    // Test for deletePayment()
    @Test
    void deletePayment_shouldDeletePayment() {
        Payment payment = new Payment();
        payment.setKey("unique-key");

        when(paymentRepository.findByKey("unique-key")).thenReturn(Optional.of(payment));

        paymentService.deletePayment("unique-key");

        verify(paymentRepository).delete(payment);
    }

    @Test
    void deletePayment_shouldThrowPaymentNotFoundException() {
        when(paymentRepository.findByKey("INVALID_KEY")).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.deletePayment("INVALID_KEY"));
    }

    // Test for getAllPayments()
    @Test
    void getAllPayments_shouldReturnListOfPaymentResponses() {
        List<Payment> payments = List.of(new Payment(), new Payment());
        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentResponse> responses = paymentService.getAllPayments();

        assertEquals(2, responses.size());
        verify(paymentRepository).findAll();
    }

    // Test for getPaymentStatus()
    @Test
    void getPaymentStatus_shouldReturnPaymentStatus() {
        Payment payment = new Payment();
        payment.setKey("unique-key");
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        when(paymentRepository.findByKey("unique-key")).thenReturn(Optional.of(payment));

        PaymentStatus status = paymentService.getPaymentStatus("unique-key");

        assertEquals(PaymentStatus.SUCCESS, status);
    }

    @Test
    void getPaymentStatus_shouldThrowPaymentNotFoundException() {
        when(paymentRepository.findByKey("INVALID_KEY")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.getPaymentStatus("INVALID_KEY"));
    }
}
