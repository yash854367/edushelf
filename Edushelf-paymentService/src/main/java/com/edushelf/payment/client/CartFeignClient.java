package com.edushelf.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edushelf.payment.dto.CartSummaryDTO;

@FeignClient(name = "order-service")
public interface CartFeignClient {

    @GetMapping("/carts/{cartKey}/summary")
    CartSummaryDTO getCartSummary(@PathVariable("cartKey") String cartKey);
}
