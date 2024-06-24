package com.practice.order.service.controller;

import com.practice.datedomains.dto.Order;
import com.practice.datedomains.dto.OrderEvent;
import com.practice.order.service.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder(order);
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is pending started");

        orderProducer.setMessage(orderEvent);

        return "Order placed successfully";
    }
}
