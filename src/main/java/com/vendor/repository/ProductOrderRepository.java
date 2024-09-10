package com.vendor.repository;

import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.vendor.model.ProductOrder;

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Integer>{

	List<ProductOrder> findByUserId(Integer userId);

}
