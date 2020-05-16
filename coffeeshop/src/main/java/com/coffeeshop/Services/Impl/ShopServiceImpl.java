package com.coffeeshop.Services.Impl;

import com.coffeeshop.DAO.ShopDAO;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ShopEntity getShop(int shopId) {
        return shopDAO.getShop(shopId);
    }
}
