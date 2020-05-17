/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Services;

import com.coffeeshop.EntityClasses.CustomerEntity;
import com.coffeeshop.EntityClasses.OrderEntity;
import com.coffeeshop.EntityClasses.ShopEntity;

import java.util.List;

public interface CustomerService {
    public CustomerEntity create(CustomerEntity customerEntity);

    public CustomerEntity login(CustomerEntity customerEntity);

    public OrderEntity createOrder(OrderEntity orderEntity);

    public OrderEntity updateOrderStatus(long orderid, String status);

    public OrderEntity changeQueue(long orderid, int queueid);

    public OrderEntity cancelOrder(long orderid);

    public OrderEntity getOrderDetails(long orderid);

    public List<OrderEntity> getQueueOrders(long queueid, long shopid);

    public int waitingCount(long queueid, long shopid);
}
