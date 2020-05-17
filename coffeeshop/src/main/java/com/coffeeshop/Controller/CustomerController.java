/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.*;
import com.coffeeshop.Enum.OrderStatusEnum;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Model.Request.CreateOrder;
import com.coffeeshop.Repository.CustomerRepository;
import com.coffeeshop.Repository.MenuRepository;
import com.coffeeshop.Repository.QueueRepository;
import com.coffeeshop.Services.CustomerService;
import com.coffeeshop.Services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ShopService shopService;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    QueueRepository queueRepository;

    @PostMapping(value = "/create")
    public @ResponseBody
    ResponseEntity<String> createShop(@RequestBody CustomerEntity customerEntity) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final CustomerEntity customerEntity1 = customerService.create(customerEntity);
            baseResponse.setError(false);
            baseResponse.setData(customerEntity1);
            baseResponse.setMessage("Created Customer Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/login")
    public @ResponseBody
    ResponseEntity<String> loginCustomer(@RequestBody CustomerEntity customerEntity) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            CustomerEntity byUsername = customerRepository.findByUsername(customerEntity.getUsername());
            if (byUsername == null) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Invalid UserName");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.NO_CONTENT);
            } else {
                if (byUsername.getPassword().equals(customerEntity.getPassword())) {
                    final CustomerEntity login = customerService.login(customerEntity);
                    baseResponse.setError(false);
                    baseResponse.setData(login);
                    baseResponse.setMessage("Customer Login Successfully");
                    baseResponse.setCode(201);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

                } else {
                    baseResponse.setError(true);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Invalid Password");
                    baseResponse.setCode(201);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.NO_CONTENT);
                }
            }
        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/order/create")
    public @ResponseBody
    ResponseEntity<String> orderCreate(@RequestBody CreateOrder createOrder) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final Optional<CustomerEntity> customer = customerRepository.findById(createOrder.getUserid());
            final Optional<ShopEntity> shop = shopService.getShop(createOrder.getShopid());
            final Optional<MenuEntity> menu = menuRepository.findById(createOrder.getMenuid());

            List<QueueEntity> queueList = queueRepository.findByShop(shop.get());
            int queue = queueList.size();
            if (queue == 0 || queue < 1) {
                queue = 1;
            }
            int queuenumber = getRandomNumberInRange(1, queue);
            QueueEntity queueEntity = queueList.get(queuenumber);

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderStatusEnum(OrderStatusEnum.PENDING.getValue());
            orderEntity.setCustomerEntity(customer.get());
            orderEntity.setLatitude(createOrder.getLatitude());
            orderEntity.setLongitude(createOrder.getLongitude());
            orderEntity.setMenuEntity(menu.get());
            orderEntity.setShop(shop.get());
            orderEntity.setQueue(queueEntity);
            final OrderEntity order = customerService.createOrder(orderEntity);

            baseResponse.setError(false);
            baseResponse.setData(order);
            baseResponse.setMessage("Order Create Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/order/update/status/{orderid}/{status}")
    public @ResponseBody
    ResponseEntity<String> updateOrderStatus(@PathVariable("orderid") long orderid,
                                             @PathVariable("status") String status) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {

            OrderEntity orderEntity = customerService.updateOrderStatus(orderid, status);
            baseResponse.setError(false);
            baseResponse.setData(orderEntity);
            baseResponse.setMessage("Order State update Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/order/update/queue/{orderid}/{queueid}")
    public @ResponseBody
    ResponseEntity<String> updateOrderQueue(@PathVariable("orderid") long orderid,
                                             @PathVariable("queueid") int queueid) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            OrderEntity orderEntity = customerService.changeQueue(orderid, queueid);
            baseResponse.setError(false);
            baseResponse.setData(orderEntity);
            baseResponse.setMessage("Order queue update Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/order/cancel/{orderid}")
    public @ResponseBody
    ResponseEntity<String> cancelOrder(@PathVariable("orderid") long orderid) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            OrderEntity orderEntity = customerService.cancelOrder(orderid);
            baseResponse.setError(false);
            baseResponse.setData(orderEntity);
            baseResponse.setMessage("Order Cancel Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/order/{orderid}")
    public @ResponseBody
    ResponseEntity<String> GetOrderDetails(@PathVariable("orderid") long orderid) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            OrderEntity orderEntity = customerService.getOrderDetails(orderid);
            baseResponse.setError(false);
            baseResponse.setData(orderEntity);
            baseResponse.setMessage("Order Details");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = "/order/queue/{orderid}/{queueid}")
    public @ResponseBody
    ResponseEntity<String> getQueueOrders(@PathVariable("orderid") long orderid,
                                          @PathVariable("queueid") long queueid) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            List<OrderEntity> queueOrders = customerService.getQueueOrders(queueid, orderid);
            baseResponse.setError(false);
            baseResponse.setData(queueOrders);
            baseResponse.setMessage("Order Details");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/order/queue/waiting/{queueid}/{shopid}")
    public @ResponseBody
    ResponseEntity<String> waitingcount(@PathVariable("queueid") long queueid,
                                        @PathVariable("shopid") long shopid) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final int i = customerService.waitingCount(queueid, shopid);
            baseResponse.setError(false);
            baseResponse.setData(i);
            baseResponse.setMessage("Order Details");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}
