package com.uicheon.orderservice.service;

import com.uicheon.orderservice.dto.OrderDto;
import com.uicheon.orderservice.jpa.OrderEntity;

public interface OrderService {
	OrderDto createOrder(OrderDto orderDetails);

	OrderDto getOrderByOrderId(String orderId);
	Iterable<OrderEntity> getOrdersByUserId(String userId);

}
