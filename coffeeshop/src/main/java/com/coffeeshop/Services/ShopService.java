package com.coffeeshop.Services;

import com.coffeeshop.EntityClasses.ShopEntity;
import org.springframework.stereotype.Service;

public interface ShopService {
    public ShopEntity create(ShopEntity shopEntity);
    public ShopEntity Update(ShopEntity shopEntity);
    public ShopEntity Delete(ShopEntity shopEntity);
    public ShopEntity getShop(long shopId);

}
