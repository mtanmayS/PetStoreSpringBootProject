package com.example.demo.controller;

import com.example.demo.bean.request.OrderRequest;
import com.example.demo.bean.response.OrderResponse;
import com.example.demo.model.Order;
import com.example.demo.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/store")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<OrderResponse>(orderService.saveOrder(orderRequest), HttpStatus.OK);

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> fetchOrderById(@PathVariable(name = "orderId",required = true) int id){
        return new ResponseEntity<OrderResponse>(orderService.fetchOrderById(id), HttpStatus.OK);

    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable(name = "orderId",required = true) int id){
        return new ResponseEntity(orderService.deleteOrderById(id), HttpStatus.OK);

    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Integer>> getInventory(){
        return new ResponseEntity<Map<String, Integer>>(orderService.getInventory(), HttpStatus.OK);
    }
}
