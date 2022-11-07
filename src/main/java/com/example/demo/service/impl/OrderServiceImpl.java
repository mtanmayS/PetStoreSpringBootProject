package com.example.demo.service.impl;

import com.example.demo.bean.request.OrderRequest;
import com.example.demo.bean.response.OrderResponse;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.exception.NoDataFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatusEnum;
import com.example.demo.model.Pet;
import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.PetStoreRepo;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    PetStoreRepo petStoreRepo;

    ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        long petId=orderRequest.getPetId();
        if(checkNull(orderRequest)){
            throw new InvalidInputException("Invalid Input");
        }
        Optional<Pet> optionalPet=petStoreRepo.findById(petId);
        if(optionalPet.isPresent()){
            Order order= new Order();
            order.setComplete(orderRequest.getComplete());
            order.setStatus(orderRequest.getStatus());
            order.setQuantity(orderRequest.getQuantity());
            order.setPetId(optionalPet.get());

           Order savedOrder= orderRepo.save(order);
           OrderResponse orderResponse = new OrderResponse();
            orderResponse= objectMapper.convertValue(orderRequest,OrderResponse.class);
            orderResponse.setShipDate(savedOrder.getShipDate());
            orderResponse.setId(savedOrder.getId());

            return orderResponse;
        }else{
            throw new InvalidDataException("Invalid Pet id");
        }


    }

    private Boolean checkNull(OrderRequest orderRequest){
        if(orderRequest.getComplete()==null||orderRequest.getStatus()==null
        || Objects.isNull(orderRequest.getQuantity())){
            return true;


        }
        return false;
    }
//    private Boolean orderStatusCheck(OrderRequest orderRequest){
//        if(orderRequest.getStatus().equals(OrderStatusEnum.approved)||
//                orderRequest.getStatus().equals(OrderStatusEnum.delivered)||
//                orderRequest.getStatus().equals(OrderStatusEnum.placed)){
//            return true;
//
//
//        }
//        return false;
//    }


    public OrderResponse fetchOrderById(int id){
        OrderResponse orderResponse = new OrderResponse();

       Optional<Order> optionalOrder= orderRepo.findById((long)id);
       if(optionalOrder.isPresent()){
           orderResponse.setComplete(optionalOrder.get().getComplete());
           orderResponse.setStatus(optionalOrder.get().getStatus());
           orderResponse.setQuantity(optionalOrder.get().getQuantity());
           orderResponse.setPetId(optionalOrder.get().getPetId().getId());
           orderResponse.setShipDate(optionalOrder.get().getShipDate());
           orderResponse.setId(optionalOrder.get().getId());
           return orderResponse;
       }else{

           throw new NoDataFoundException("Order not found");
       }


    }

    public String deleteOrderById(int id){
        if(orderRepo.findById((long) id).isPresent()){
            orderRepo.deleteById((long) id);
            return "Order Deleted";
        }else{
            throw new NoDataFoundException("Order not found");
        }


    }

    public Map<String, Integer> getInventory(){
        List<Order> orders = orderRepo.findAll();
        return getInventoryStatus(orders);

    }

    private static Map<String, Integer> getInventoryStatus(List<Order> orderList) {
        Map<String, Integer> inventory = new HashMap<>();
        orderList.forEach(order->{
            Integer quantity = inventory.getOrDefault(order.getStatus().toString(),0);
            inventory.put(order.getStatus().toString(), (int) (quantity+order.getQuantity()));
        });
        return inventory;
    }
}
