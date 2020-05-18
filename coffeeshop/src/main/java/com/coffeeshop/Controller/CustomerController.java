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
import com.coffeeshop.Model.NearestShops;
import com.coffeeshop.Model.Request.CreateOrder;
import com.coffeeshop.Repository.CustomerRepository;
import com.coffeeshop.Repository.MenuRepository;
import com.coffeeshop.Repository.QueueRepository;
import com.coffeeshop.Services.CustomerService;
import com.coffeeshop.Services.ShopService;
import com.coffeeshop.Utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.util.SloppyMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Value("${air.distance.limit}")
    private double airdistanceLimit;

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
    ResponseEntity<String> orderCreate(@RequestBody CreateOrder createOrder,
                                       @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

            final Optional<CustomerEntity> customer = customerRepository.findById(createOrder.getUserid());
            final Optional<ShopEntity> shop = shopService.getShop(createOrder.getShopid());
            final Optional<MenuEntity> menu = menuRepository.findById(createOrder.getMenuid());

            List<QueueEntity> queueList = queueRepository.findAll();
            for (QueueEntity queueEntity : queueList) {
                if (queueEntity.getShop().getId() != shop.get().getId()) {
                    queueList.remove(queueEntity);
                }
            }
            int queuenumber = 0;
            int queue = queueList.size();
            if (queue == 0 || queue < 1) {
                queue = 1;
            } else if (queue == 1) {
                queuenumber = 0;
            } else {
                queuenumber = Utils.getRandomNumberInRange(1, queue);
            }

            QueueEntity queueEntity = queueList.get(queuenumber);

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderStatusEnum(OrderStatusEnum.PENDING.getValue());
            orderEntity.setCustomerEntity(customer.get());
            orderEntity.setLatitude(createOrder.getLatitude());
            orderEntity.setLongitude(createOrder.getLongitude());
            orderEntity.setMenuEntity(menu.get());
            orderEntity.setShopEntity(shop.get());
            orderEntity.setQueueEntity(queueEntity);
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
                                             @PathVariable("status") String status,
                                             @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
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
                                            @PathVariable("queueid") int queueid,
                                            @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

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
    ResponseEntity<String> cancelOrder(@PathVariable("orderid") long orderid,
                                       @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

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
    ResponseEntity<String> GetOrderDetails(@PathVariable("orderid") long orderid,
                                           @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

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


    @GetMapping(value = "/order/queue/{queueid}/{shopid}")
    public @ResponseBody
    ResponseEntity<String> getQueueOrders(@PathVariable("queueid") long queueid,
                                          @PathVariable("shopid") long shopid,
                                          @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

            List<OrderEntity> queueOrders = customerService.getQueueOrders(queueid, shopid);
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
                                        @PathVariable("shopid") long shopid,
                                        @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

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



    @GetMapping(value = "/nearestshops/{lat1}/{long1}")
    public @ResponseBody
    ResponseEntity<String> nearestshops(@PathVariable("lat1") double lat1,
                                        @PathVariable("long1") double long1) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            List<NearestShops> filtered = new ArrayList<>();
            List<ShopEntity> allShops = customerService.getAllShops();
            for (ShopEntity shopEntity : allShops) {
                double distance = Utils.getAirDistance(lat1, long1, Double.parseDouble(shopEntity.getLatitude()), Double.parseDouble(shopEntity.getLongitude()));
                if (distance <= airdistanceLimit) {
                    NearestShops nearestShops = new NearestShops();
                    nearestShops.setDistance(distance);
                    nearestShops.setClosetime(shopEntity.getClosetime());
                    nearestShops.setId(shopEntity.getId());
                    nearestShops.setLatitude(shopEntity.getLatitude());
                    nearestShops.setLongitude(shopEntity.getLongitude());
                    nearestShops.setOpentime(shopEntity.getOpentime());
                    nearestShops.setPhoneNumber(shopEntity.getPhoneNumber());
                    nearestShops.setShopAddress(shopEntity.getShopAddress());
                    nearestShops.setShopName(shopEntity.getShopName());
                    filtered.add(nearestShops);
                }

            }

            baseResponse.setError(false);
            baseResponse.setData(filtered);
            baseResponse.setMessage("Nearest Shop Details");
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

    public boolean validateToken(String token) {
        String[] arr = token.split(" ");
        if (arr[1] != null) {
            final CustomerEntity byToken = customerRepository.findByToken(arr[1]);
            if (byToken != null) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }


}
