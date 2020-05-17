package com.coffeeshop.Services.Impl;

import com.coffeeshop.DAO.ShopDAO;
import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.QueueEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.EntityClasses.UserEntity;
import com.coffeeshop.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDAO shopDAO;

    @Override
    public ShopEntity create(ShopEntity shopEntity) {
        return shopDAO.create(shopEntity);
    }

    @Override
    public ShopEntity Update(ShopEntity shopEntity) {
        return shopDAO.Update(shopEntity);
    }

    @Override
    public ShopEntity Delete(ShopEntity shopEntity) {
        return shopDAO.Update(shopEntity);
    }

    @Override
    public Optional<ShopEntity> getShop(long shopId) {
        return shopDAO.getShop(shopId);
    }

    @Override
    public MenuEntity createMenu(MenuEntity menuEntity) {
        return shopDAO.createMenu(menuEntity);
    }

    @Override
    public List<MenuEntity> getMenuesByShop(long shopid) {
        return shopDAO.getMenuesByShop(shopid);
    }

    @Override
    public MenuEntity updateMenu(MenuEntity menuEntity) {
        return shopDAO.updateMenu(menuEntity);
    }

    @Override
    public QueueEntity CreateQueue(QueueEntity queueEntity) {
        return shopDAO.CreateQueue(queueEntity);
    }

    @Override
    public QueueEntity UpdateQueue(QueueEntity queueEntity) {
        return shopDAO.UpdateQueue(queueEntity);
    }

    @Override
    public QueueEntity DeleteQueue(QueueEntity queueEntity) {
        return shopDAO.DeleteQueue(queueEntity);
    }

    @Override
    public UserEntity CreateUser(UserEntity userEntity) {
        return shopDAO.CreateUser(userEntity);
    }
}
