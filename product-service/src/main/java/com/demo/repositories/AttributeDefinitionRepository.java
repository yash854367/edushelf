package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entities.AttributeDefinition;

@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition, Long> {}