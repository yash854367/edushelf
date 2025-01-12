package com.edushelf.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edushelf.payment.entity.Payment;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByKey(String key);
    Optional<Payment> findByPaymentReference(String paymentReference);
}