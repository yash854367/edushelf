package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Query("SELECT pt FROM ProductType pt WHERE pt.name LIKE %:name%")
    List<ProductType> findByNameContaining(@Param("name") String name);
}
