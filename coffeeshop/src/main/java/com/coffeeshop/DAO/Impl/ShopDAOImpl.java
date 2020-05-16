package com.coffeeshop.DAO.Impl;

import com.coffeeshop.DAO.ShopDAO;
import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Repository.MenuRepository;
import com.coffeeshop.Repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopDAOImpl implements ShopDAO {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    MenuRepository menuRepository;

    @Override
    public ShopEntity create(ShopEntity shopEntity) {

        return shopRepository.save(shopEntity);
    }

    @Override
    public ShopEntity Update(ShopEntity shopEntity) {
        final Optional<ShopEntity> byId = shopRepository.findById(shopEntity.getId());
        if(byId.get()!=null){
            if (shopEntity.getClosetime()!=null){
                byId.get().setClosetime(shopEntity.getClosetime());
            }
            if (shopEntity.getLatitude()!=null){
                byId.get().setLatitude(shopEntity.getLatitude());
            }
            if (shopEntity.getLongitude()!=null){
                byId.get().setLongitude(shopEntity.getLongitude());
            }
            if (shopEntity.getOpentime()!=null){
                byId.get().setOpentime(shopEntity.getOpentime());
            }
            if (shopEntity.getPhoneNumber()!=null){
                byId.get().setPhoneNumber(shopEntity.getPhoneNumber());
            }
            if (shopEntity.getShopAddress()!=null){
                byId.get().setShopAddress(shopEntity.getShopAddress());
            }
            if (shopEntity.getShopName()!=null){
                byId.get().setShopName(shopEntity.getShopName());
            }
        }


        return shopRepository.save(byId.get());
    }

    @Override
    public ShopEntity Delete(ShopEntity shopEntity) {
        shopRepository.deleteById(shopEntity.getId());
        return shopEntity;
    }

    @Override
    public Optional<ShopEntity> getShop(long shopId) {

        return shopRepository.findById(shopId);


    }

    @Override
    public MenuEntity createMenu(MenuEntity menuEntity) {
        return menuRepository.save(menuEntity);
    }

    @Override
    public MenuEntity updateMenu(MenuEntity menuEntity) {
        final Optional<MenuEntity> byId = menuRepository.findById(menuEntity.getId());
        if(menuEntity.getName().equals(byId.get().getName())){
           //do not nothing
        }else{
            byId.get().setName(menuEntity.getName());
        }
        if(menuEntity.getShop().getId()==byId.get().getShop().getId()){
            //do not nothing
        }else{
            byId.get().setShop(menuEntity.getShop());
        }
        return menuRepository.save(byId.get());
    }
}
