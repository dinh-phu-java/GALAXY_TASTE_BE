package com.galaxytaste.service.impl;

import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.Order;
import com.galaxytaste.domain.OrderDetail;
import com.galaxytaste.domain.User;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.repository.OrderRepository;
import com.galaxytaste.repository.UserRepository;
import com.galaxytaste.service.CartItemService;
import com.galaxytaste.service.OrderService;
import com.galaxytaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.galaxytaste.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @Override
    public Order createOrder(String username) throws UserNotFoundException {
        User user=this.userService.findUserByUsername(username);
        if(user == null){
            throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME);
        }
        Cart userCart= user.getCart();
        Order order=new Order(user, new Date(), null, false );
        order.setUser(user);
        userCart.getCartItems().forEach(item->{
            OrderDetail orderDetail=new OrderDetail(order, item.getProductId(), item.getProductImage(), item.getPrice(), item.getAmount(), item.getProductName()) ;
            order.addToOrderList(orderDetail);
        });
        this.cartItemService.deleteAllCartItemByUsername(username);
        this.orderRepository.save(order);
        return order;
    }
}
