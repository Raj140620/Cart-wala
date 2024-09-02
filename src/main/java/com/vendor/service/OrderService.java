package com.vendor.service;

import com.vendor.model.OrderRequest;
import com.vendor.model.ProductOrder;

public interface OrderService {
	
	public void saveOrder(Integer userId,OrderRequest orderRequest);

}
