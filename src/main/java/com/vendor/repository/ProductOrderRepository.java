package com.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendor.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer>{

}
