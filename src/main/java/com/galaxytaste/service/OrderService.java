package com.galaxytaste.service;

import com.galaxytaste.domain.Order;
import com.galaxytaste.exception.domain.UserNotFoundException;

public interface OrderService {
    Order createOrder(String username) throws UserNotFoundException;
}
