package com.galaxytaste.controller;

import com.galaxytaste.domain.Order;
import com.galaxytaste.domain.OrderDetail;
import com.galaxytaste.domain.User;
import com.galaxytaste.repository.OrderRepository;
import com.galaxytaste.repository.UserRepository;
import com.galaxytaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
@Autowired
private UserService userService;

    @PostMapping("order")
    public ResponseEntity<Order> placeOrder(){
        User user=this.userService.findUserByUsername("dinhphu");
        Order order=new Order(user, new Date(), new Date(new Date().getTime()+50000), false );
        order.setUser(user);
        OrderDetail orderdetail1=new OrderDetail(order, 1L, "abc.jpg", 5, 5,  "productName1");
        OrderDetail orderdetail2=new OrderDetail(order, 2L, "abc1.jpg", 15, 2,  "productName2");
        order.addToOrderList(orderdetail1);
        order.addToOrderList(orderdetail2);
        this.orderRepository.save(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
