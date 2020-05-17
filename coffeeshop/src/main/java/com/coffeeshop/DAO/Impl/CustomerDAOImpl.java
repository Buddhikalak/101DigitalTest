/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.DAO.Impl;

import com.coffeeshop.DAO.CustomerDAO;
import com.coffeeshop.EntityClasses.CustomerEntity;
import com.coffeeshop.EntityClasses.OrderEntity;
import com.coffeeshop.EntityClasses.QueueEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Enum.OrderStatusEnum;
import com.coffeeshop.Repository.CustomerRepository;
import com.coffeeshop.Repository.OrderRepository;
import com.coffeeshop.Repository.QueueRepository;
import com.coffeeshop.Repository.ShopRepository;
import com.coffeeshop.Utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    QueueRepository queueRepository;


    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customerEntity.setTimestamp(timestamp);
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity login(CustomerEntity customerEntity) {
        CustomerEntity byUsername = customerRepository.findByUsername(customerEntity.getUsername());
        if (byUsername.getPassword().equals(customerEntity.getPassword())) {
            byUsername.setToken(TokenGenerator.generateNewToken());
        }

        return customerRepository.save(byUsername);
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        orderEntity.setDate(timestamp);
        orderEntity.setOrderStatusEnum(OrderStatusEnum.PENDING.getValue());
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity updateOrderStatus(long orderid, String status) {
        Optional<OrderEntity> byId = orderRepository.findById(orderid);
        if (byId.get() != null) {
            String statusup = status.toUpperCase();
            if (statusup.equals(OrderStatusEnum.PENDING.getValue())) {
                byId.get().setOrderStatusEnum(OrderStatusEnum.PENDING.getValue());
            }
            if (statusup.equals(OrderStatusEnum.PREPARED.getValue())) {
                byId.get().setOrderStatusEnum(OrderStatusEnum.PREPARED.getValue());
            }
            if (statusup.equals(OrderStatusEnum.COMPLETED.getValue())) {
                byId.get().setOrderStatusEnum(OrderStatusEnum.COMPLETED.getValue());
            }
            if (statusup.equals(OrderStatusEnum.CANCELLED.getValue())) {
                byId.get().setOrderStatusEnum(OrderStatusEnum.CANCELLED.getValue());
            }
            final OrderEntity save = orderRepository.save(byId.get());
            return save;
        }
        return null;
    }

    @Override
    public OrderEntity changeQueue(long orderid, int queueid) {
        Optional<OrderEntity> byId = orderRepository.findById(orderid);
        if (byId.get() != null) {
            Optional<ShopEntity> shop = shopRepository.findById(byId.get().getShopEntity().getId());
            if (shop.get() != null) {
                List<QueueEntity> queueList = queueRepository.findAll();
                for (QueueEntity queueEntity : queueList) {
                    if (queueEntity.getShop().getId() != shop.get().getId()) {
                        queueList.remove(queueEntity);
                    }
                }
                int queue = queueList.size();
                byId.get().setQueueEntity(queueRepository.findById((long) queueid).get());
                return orderRepository.save(byId.get());
            }
        }
        return null;
    }

    @Override
    public OrderEntity cancelOrder(long orderid) {
        Optional<OrderEntity> byId = orderRepository.findById(orderid);
        if (byId.get() != null) {
            byId.get().setOrderStatusEnum(OrderStatusEnum.CANCELLED.getValue());
            return orderRepository.save(byId.get());
        }
        return null;
    }

    @Override
    public OrderEntity getOrderDetails(long orderid) {
        return orderRepository.findById(orderid).get();
    }

    @Override
    public List<OrderEntity> getQueueOrders(long queueid, long shopid) {
        final Optional<QueueEntity> queue = queueRepository.findById(queueid);
        final Optional<ShopEntity> shop = shopRepository.findById(shopid);
        if (queue.get() != null && shop.get() != null) {
            List<OrderEntity> orderRepositoryAll2 = new ArrayList<>();
            final List<OrderEntity> orderRepositoryAll = orderRepository.findAll();
            for (OrderEntity entity : orderRepositoryAll) {
                if (entity.getShopEntity().getId() == shop.get().getId()) {
                    if (entity.getQueueEntity().getId() == queue.get().getId()) {
                        orderRepositoryAll2.add(entity);
                    } else {
                       // orderRepositoryAll.remove(entity);
                    }
                } else {
                    //orderRepositoryAll.remove(entity);
                }
            }
            return orderRepositoryAll2;
        }
        return null;
    }

    @Override
    public int waitingCount(long queueid, long shopid) {
        final Optional<QueueEntity> queue = queueRepository.findById(queueid);
        final Optional<ShopEntity> shop = shopRepository.findById(shopid);
        if (queue.get() != null && shop.get() != null) {
            final List<OrderEntity> orderRepositoryAll2 =new ArrayList<>();
            final List<OrderEntity> orderRepositoryAll = orderRepository.findAll();
            for (OrderEntity entity : orderRepositoryAll) {
                if (entity.getShopEntity().getId() == shop.get().getId()) {
                    if (entity.getQueueEntity().getId() == queue.get().getId()) {
                        if (entity.getOrderStatusEnum().equals(OrderStatusEnum.PENDING.getValue())) {
                            orderRepositoryAll2.add(entity);
                        } else {
                            //orderRepositoryAll.remove(entity);
                        }
                    } else {
                        //orderRepositoryAll.remove(entity);
                    }

                } else {
                   // orderRepositoryAll.remove(entity);
                }
            }
            return orderRepositoryAll2.size();// return orderRepository.findByShopAndQueueAndOrderStatusEnum(shop.get(), queue.get(), OrderStatusEnum.PENDING.getValue()).size();
        }
        return 0;
    }
}
