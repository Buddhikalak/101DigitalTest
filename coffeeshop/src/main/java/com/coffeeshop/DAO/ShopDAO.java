package com.coffeeshop.DAO;

import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.ShopEntity;

import java.util.Optional;

public interface ShopDAO {
    public ShopEntity create(ShopEntity shopEntity);
    public ShopEntity Update(ShopEntity shopEntity);
    public ShopEntity Delete(ShopEntity shopEntity);
    public Optional<ShopEntity> getShop(long shopId);

    public MenuEntity createMenu(MenuEntity menuEntity);
    public MenuEntity updateMenu(MenuEntity menuEntity);
}
