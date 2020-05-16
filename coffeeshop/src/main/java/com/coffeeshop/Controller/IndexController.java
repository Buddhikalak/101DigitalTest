package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import com.coffeeshop.EntityClasses.UserEntity;
import com.coffeeshop.Enum.UserRoleEnum;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Model.Request.CreateMenu;
import com.coffeeshop.Repository.UserRepository;
import com.coffeeshop.Utils.TokenGenerator;
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
public class IndexController {
    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> Test() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {

            UserEntity userEntity  = new UserEntity();
            userEntity.setPassword("123");
            userEntity.setRole(UserRoleEnum.SHOP_OWNER.getValue());
            userEntity.setUserName("owner");
            userRepository.save(userEntity);


            baseResponse.setError(false);
            baseResponse.setData(null);
            baseResponse.setMessage("1.0.0");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
        } catch (Exception ex) {
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

    @PostMapping(value = "/authenticate")
    public @ResponseBody
    ResponseEntity<String> Authenticate(@RequestBody UserEntity userEntity) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            final UserEntity byUserName = userRepository.findByUserName(userEntity.getUserName());
            if (byUserName != null) {
                if (byUserName.getPassword().equals(userEntity.getPassword())) {
                    final String token = TokenGenerator.generateNewToken();
                    byUserName.setToken(token);
                    userRepository.save(byUserName);
                    baseResponse.setError(false);
                    baseResponse.setData(byUserName);
                    baseResponse.setMessage("Aunthenticated");
                    baseResponse.setCode(200);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

                }else{
                    baseResponse.setError(false);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Password Invalid");
                    baseResponse.setCode(401);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
                }
            }else{
                baseResponse.setError(false);
                baseResponse.setData(null);
                baseResponse.setMessage("Username Invalid");
                baseResponse.setCode(401);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
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
