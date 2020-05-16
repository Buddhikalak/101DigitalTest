package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Model.Request.CreateMenu;
import com.coffeeshop.Services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    ShopService shopService;

    @PostMapping(value = "/create")
    public @ResponseBody
    ResponseEntity<String> createShop(@RequestBody CreateMenu createMenu){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final Optional<ShopEntity> shop = shopService.getShop(createMenu.getShopid());
            if(shop.get()!=null){
                MenuEntity menuEntity=new MenuEntity();
                menuEntity.setShop(shop.get());
                menuEntity.setName(createMenu.getName());
                final MenuEntity entity = shopService.createMenu(menuEntity);
                baseResponse.setError(false);
                baseResponse.setData(entity);
                baseResponse.setMessage("Created Shop Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            }else{
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Shop is not valid");
                baseResponse.setCode(500);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception ex){
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
