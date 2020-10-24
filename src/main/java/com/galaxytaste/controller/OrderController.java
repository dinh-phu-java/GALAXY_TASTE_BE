package com.galaxytaste.controller;

import com.galaxytaste.domain.Order;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.repository.OrderRepository;
import com.galaxytaste.service.OrderService;
import com.galaxytaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    //need too token for place order
    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestParam String username) throws UserNotFoundException {
        Order order = this.orderService.createOrder(username);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
