package com.example.crudsample.dao;

import com.example.crudsample.ds.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product,Integer> {
}
