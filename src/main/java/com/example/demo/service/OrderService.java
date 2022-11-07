package com.example.demo.service;

import com.example.demo.bean.request.OrderRequest;
import com.example.demo.bean.response.OrderResponse;

public interface OrderService {

    OrderResponse saveOrder(OrderRequest orderRequest);
}
