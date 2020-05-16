package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Services.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopper")
public class ShopController {
    @Autowired
    ShopService shopService;

    @PostMapping(value = "create")
    public @ResponseBody
    ResponseEntity<String> createShop(@RequestBody ShopEntity shopEntity){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final ShopEntity entity = shopService.create(shopEntity);
            baseResponse.setError(false);
            baseResponse.setData(entity);
            baseResponse.setMessage("Created Shop Successfully");
            baseResponse.setCode(201);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        }catch (Exception ex){
            baseResponse.setError(true);
            baseResponse.setData(null);
            baseResponse.setMessage(ex.getMessage().toString());
            baseResponse.setCode(500);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
