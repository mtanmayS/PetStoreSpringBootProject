package com.pet.store.demo.dao;

import com.pet.store.demo.model.Order;
import com.pet.store.demo.request.OrderRequest;
import com.pet.store.demo.response.OrderResponse;

public interface OrderRepository {

    OrderResponse saveOrder(OrderRequest orderRequest);
    OrderResponse fetchOrderById(long id);
    String deleteOrderById(long id);



}
