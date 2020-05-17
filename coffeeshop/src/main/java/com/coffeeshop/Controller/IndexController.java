package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.UserEntity;
import com.coffeeshop.Enum.UserRoleEnum;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Repository.UserRepository;
import com.coffeeshop.Utils.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "CoffeeShop Management System",description = "Test the Api and Authenticate Sub Users")
public class IndexController {
    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/")
    @ApiOperation(value = "Test The Api", response = BaseRestResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public @ResponseBody
    ResponseEntity<String> Test() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {

            UserEntity userEntity = new UserEntity();
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
    @ApiOperation(value = "Authenticate The Sub Users", response = BaseRestResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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

                } else {
                    baseResponse.setError(false);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Password Invalid");
                    baseResponse.setCode(401);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
                }
            } else {
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
