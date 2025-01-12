package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	Price findFirstBySku(String sku);
}
