package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.EntityClasses.UserEntity;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Repository.UserRepository;
import com.coffeeshop.Services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shopper")
public class ShopController {
    @Autowired
    ShopService shopService;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/create")
    public @ResponseBody
    ResponseEntity<String> createShop(@RequestBody ShopEntity shopEntity,
                                      @RequestHeader("Authorization") String Authorization){

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth=validateToken(Authorization);
            if(!Auth){
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            final ShopEntity entity = shopService.create(shopEntity);
            baseResponse.setError(false);
            baseResponse.setData(entity);
            baseResponse.setMessage("Created Shop Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

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

    public boolean validateToken(String token) {
        String[] arr = token.split(" ");
        if (arr[1] != null) {
            final UserEntity byToken = userRepository.findByToken(arr[1]);
            if (byToken != null) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }


    @PutMapping(value = "/update")
    public @ResponseBody
    ResponseEntity<String> updateShop(@RequestBody ShopEntity shopEntity){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final ShopEntity entity = shopService.Update(shopEntity);
            baseResponse.setError(false);
            baseResponse.setData(entity);
            baseResponse.setMessage("Updated Shop Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

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
    @DeleteMapping(value = "/delete")
    public @ResponseBody
    ResponseEntity<String> deleteShop(@RequestBody ShopEntity shopEntity){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final ShopEntity entity = shopService.Delete(shopEntity);
            baseResponse.setError(false);
            baseResponse.setData(entity);
            baseResponse.setMessage("Deleted Shop Successfully");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

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

    @GetMapping(value = "/get/{id}")
    public @ResponseBody
    ResponseEntity<String> deleteShop(@PathVariable("id") long id){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final Optional<ShopEntity> entity = shopService.getShop(id);
            baseResponse.setError(false);
            if(entity==null){
                baseResponse.setData(null);
                baseResponse.setMessage("No Shops");
            }else{
                baseResponse.setData(entity.get());
                baseResponse.setMessage("Shop Details");
            }

            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        }catch (Exception ex){
            baseResponse.setError(false);
            baseResponse.setData(null);
            baseResponse.setMessage("No Shops");
            baseResponse.setCode(201);
            try {
                responseJson = mapper.writeValueAsString(baseResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
        }
    }





}
