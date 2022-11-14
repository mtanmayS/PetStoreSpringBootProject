package com.pet.store.demo.dao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.store.demo.exception.InvalidDataException;
import com.pet.store.demo.exception.InvalidInputException;
import com.pet.store.demo.exception.NoDataFoundException;
import com.pet.store.demo.model.Category;
import com.pet.store.demo.model.Order;
import com.pet.store.demo.model.OrderStatusEnum;
import com.pet.store.demo.request.OrderRequest;
import com.pet.store.demo.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.util.EnumUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PetRepositoryImpl petRepository;
    ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    private static final String INSERT_INTO_ORDER="Insert into " +
            "order_table(order_pet_id,quantity,order_status,complete_status)" +
            " values(?,?,?,?)";

    private static final String SELECT_FROM_ORDER="Select * from order_table where order_id=?";

    private static final String DELETE_ORDER="Delete from order_table where order_id=?";

    private static final String GET_ALL="Select * from order_table";

    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        long petId=orderRequest.getPetId();
        long orderId;
        if(null!=petRepository.getPetById(petId)){

           try {


            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_INTO_ORDER,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1,petId);
                ps.setLong(2,orderRequest.getQuantity());
                ps.setNString(3,orderRequest.getStatus().name());
                ps.setBoolean(4,orderRequest.getComplete());


                return ps;
            }, keyHolder);
            orderId =keyHolder.getKey().longValue();

                return fetchOrderById(orderId);
            }catch (Exception e){
               throw new InvalidInputException("Invalid Input");
            }

        }else{
            throw new InvalidDataException("Invalid Pet Id");
        }


    }

    @Override
    public OrderResponse fetchOrderById(long id) {
        Order order;
        try{
            order= jdbcTemplate.queryForObject(SELECT_FROM_ORDER,
                    new Object[]{id},(rs, rowNum) ->
                            new Order(rs.getLong("order_id"),
                                    rs.getLong("order_pet_id"),
                                    rs.getLong("quantity"),
                                    (LocalDateTime) rs.getObject("ship_date"),
                                    rs.getString("order_status"),
                                    rs.getBoolean("complete_status")));
            return savedOrder(order);

        }catch (Exception e){
            throw new NoDataFoundException("Order not found");
        }

    }

    private OrderResponse savedOrder(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setShipDate(order.getShipDate());
        orderResponse.setPetId(order.getPetId());
        orderResponse.setId(order.getId());
        orderResponse.setStatus(OrderStatusEnum.valueOf(order.getStatus()));
        orderResponse.setComplete(order.getComplete());
        orderResponse.setQuantity(order.getQuantity());

        return orderResponse;

    }

    @Override
    public String deleteOrderById(long id) {
      if(null!=fetchOrderById(id)){
          jdbcTemplate.update(DELETE_ORDER,id);
          return "Order Deleted";
      }else{
          throw new NoDataFoundException("Order not found");
      }
    }

    public Map<String, Integer> getInventory(){
        List<Order> orders = jdbcTemplate.query(GET_ALL,(rs,rowNum)->new Order(rs.getLong("order_id"),
                        rs.getLong("order_pet_id"),
                        rs.getLong("quantity"),
                        (LocalDateTime) rs.getObject("ship_date"),
                        rs.getString("order_status"),
                        rs.getBoolean("complete_status"))
                );
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
