package com.coffeeshop.DAO.Impl;

import com.coffeeshop.DAO.ShopDAO;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ShopDAOImpl implements ShopDAO {

    @Autowired
    ShopRepository shopRepository;

    @Override
    public ShopEntity create(ShopEntity shopEntity) {

        return shopRepository.save(shopEntity);
    }

    @Override
    public ShopEntity Update(ShopEntity shopEntity) {
        return shopRepository.save(shopEntity);
    }

    @Override
    public ShopEntity Delete(ShopEntity shopEntity) {
        shopRepository.delete(shopEntity);
        return shopEntity;
    }

    @Override
    public ShopEntity getShop(long shopId) {

        return shopRepository.getOne(shopId);
    }
}
