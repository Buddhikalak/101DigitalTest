/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Services.Impl;

import com.coffeeshop.DAO.CustomerDAO;
import com.coffeeshop.EntityClasses.CustomerEntity;
import com.coffeeshop.EntityClasses.OrderEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {

        return customerDAO.create(customerEntity);
    }

    @Override
    public CustomerEntity login(CustomerEntity customerEntity) {
        return customerDAO.login(customerEntity);
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        return customerDAO.createOrder(orderEntity);
    }

    @Override
    public OrderEntity updateOrderStatus(long orderid, String status) {
        return customerDAO.updateOrderStatus(orderid, status);
    }

    @Override
    public OrderEntity changeQueue(long orderid, int queueid) {
        return customerDAO.changeQueue(orderid, queueid);
    }

    @Override
    public OrderEntity cancelOrder(long orderid) {
        return customerDAO.cancelOrder(orderid);
    }

    @Override
    public OrderEntity getOrderDetails(long orderid) {
        return customerDAO.getOrderDetails(orderid);
    }

    @Override
    public List<OrderEntity> getQueueOrders(long queueid, long shopid) {
        return customerDAO.getQueueOrders(queueid, shopid);
    }

    @Override
    public int waitingCount(long queueid, long shopid) {
        return customerDAO.waitingCount(queueid, shopid);
    }

    @Override
    public List<ShopEntity> getAllShops() {
        return customerDAO.getAllShops();
    }
}
