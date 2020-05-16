package com.coffeeshop.DAO;

import com.coffeeshop.EntityClasses.ShopEntity;

public interface ShopDAO {
    public ShopEntity create(ShopEntity shopEntity);
    public ShopEntity Update(ShopEntity shopEntity);
    public ShopEntity Delete(ShopEntity shopEntity);
    public ShopEntity getShop(int shopId);
}
