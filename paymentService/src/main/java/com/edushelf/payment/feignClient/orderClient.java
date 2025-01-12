package com.edushelf.payment.feignClient;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="EduShelf-OrderService")
public interface orderClient {
 
	
}
