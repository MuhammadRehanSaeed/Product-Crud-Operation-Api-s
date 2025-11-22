package com.rehancode.phase1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.phase1.Entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Integer> {
    
}
