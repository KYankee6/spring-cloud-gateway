package com.uicheon.orderservice.controller;

import static org.modelmapper.convention.MatchingStrategies.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uicheon.orderservice.dto.OrderDto;
import com.uicheon.orderservice.jpa.OrderEntity;
import com.uicheon.orderservice.messagequeue.KafkaProducer;
import com.uicheon.orderservice.messagequeue.OrderProducer;
import com.uicheon.orderservice.service.OrderService;
import com.uicheon.orderservice.vo.RequestOrder;
import com.uicheon.orderservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final Environment env;
	private final OrderService orderService;
	private final KafkaProducer kafkaProducer;
	private final OrderProducer orderProducer;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));

	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails) throws Exception{
		log.info("Before add order data");

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(STRICT);

		OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
		orderDto.setUserId(userId);

		/* jpa */
		OrderDto createdOrder = orderService.createOrder(orderDto);
		ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

		/* kafka */
		// orderDto.setOrderId(UUID.randomUUID().toString());
		// orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

		/* send this order to kafka */
		kafkaProducer.send("example-catalog-topic", orderDto);
		// orderProducer.send("orders", orderDto);

		// ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

		log.info("After add order data");

		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception {
		log.info("Before retrieve order data");
		Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

		List<ResponseOrder> result = new ArrayList<>();
		orderList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseOrder.class));
		});

		// try {
		// 	Thread.sleep(1000);
		// 	throw new Exception("장애 발생");
		// } catch (InterruptedException exception) {
		// 	log.warn(exception.getMessage());
		//
		// }
		log.info("After retrieve order data");

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
