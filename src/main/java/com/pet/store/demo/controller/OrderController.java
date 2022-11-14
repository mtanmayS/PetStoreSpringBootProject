package com.pet.store.demo.controller;


import com.pet.store.demo.dao.OrderRepositoryImpl;
import com.pet.store.demo.request.OrderRequest;
import com.pet.store.demo.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/store")
public class OrderController {

    @Autowired
    OrderRepositoryImpl orderRepository;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<OrderResponse>(orderRepository.saveOrder(orderRequest), HttpStatus.OK);

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> fetchOrderById(@PathVariable(name = "orderId",required = true) int id){
        return new ResponseEntity<OrderResponse>(orderRepository.fetchOrderById(id), HttpStatus.OK);

    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable(name = "orderId",required = true) int id){
        return new ResponseEntity(orderRepository.deleteOrderById(id), HttpStatus.OK);

    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Integer>> getInventory(){
        return new ResponseEntity<Map<String, Integer>>(orderRepository.getInventory(), HttpStatus.OK);
    }
}
