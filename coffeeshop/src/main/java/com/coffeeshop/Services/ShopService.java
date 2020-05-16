package com.coffeeshop.Services;

import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.QueueEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.EntityClasses.UserEntity;

import java.util.Optional;

public interface ShopService {
    public ShopEntity create(ShopEntity shopEntity);

    public ShopEntity Update(ShopEntity shopEntity);

    public ShopEntity Delete(ShopEntity shopEntity);

    public Optional<ShopEntity> getShop(long shopId);

    public MenuEntity createMenu(MenuEntity menuEntity);

    public MenuEntity updateMenu(MenuEntity menuEntity);

    public QueueEntity CreateQueue(QueueEntity queueEntity);

    public QueueEntity UpdateQueue(QueueEntity queueEntity);

    public QueueEntity DeleteQueue(QueueEntity queueEntity);

    public UserEntity CreateUser(UserEntity userEntity);


}
