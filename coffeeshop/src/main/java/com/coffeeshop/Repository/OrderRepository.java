/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Repository;

import com.coffeeshop.EntityClasses.OrderEntity;
import com.coffeeshop.EntityClasses.QueueEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    public List<OrderEntity> findByShopAndQueue(ShopEntity shop, QueueEntity queue);
    public List<OrderEntity> findByShopAndQueueAndOrderStatusEnum(ShopEntity shop, QueueEntity queue,String Status);

}
