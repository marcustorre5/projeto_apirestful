package com.example.projeto_apirest.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto_apirest.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID>{



}
