package com.coffeeshop.Controller;

import com.coffeeshop.EntityClasses.*;
import com.coffeeshop.Enum.UserRoleEnum;
import com.coffeeshop.Model.BaseRestResponse;
import com.coffeeshop.Model.Request.CreateQueue;
import com.coffeeshop.Model.Request.CreateUser;
import com.coffeeshop.Repository.QueueRepository;
import com.coffeeshop.Repository.UserRepository;
import com.coffeeshop.Services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shopper")
public class ShopController {
    @Autowired
    ShopService shopService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QueueRepository queueRepository;

    @PostMapping(value = "/create")
    public @ResponseBody
    ResponseEntity<String> createShop(@RequestBody ShopEntity shopEntity,
                                      @RequestHeader("Authorization") String Authorization) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue())) {
                final ShopEntity entity = shopService.create(shopEntity);
                baseResponse.setError(false);
                baseResponse.setData(entity);
                baseResponse.setMessage("Created Shop Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You haven't Permission");
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
    ResponseEntity<String> updateShop(@RequestBody ShopEntity shopEntity,
                                      @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue())) {
                final ShopEntity entity = shopService.Update(shopEntity);
                baseResponse.setError(false);
                baseResponse.setData(entity);
                baseResponse.setMessage("Updated Shop Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You haven't Permission");
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

    @DeleteMapping(value = "/delete")
    public @ResponseBody
    ResponseEntity<String> deleteShop(@RequestBody ShopEntity shopEntity,
                                      @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue())) {
                final ShopEntity entity = shopService.Delete(shopEntity);
                baseResponse.setError(false);
                baseResponse.setData(entity);
                baseResponse.setMessage("Deleted Shop Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You haven't Permission");
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

    @GetMapping(value = "/get/{id}")
    public @ResponseBody
    ResponseEntity<String> deleteShop(@PathVariable("id") long id,
                                      @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

            final Optional<ShopEntity> entity = shopService.getShop(id);
            baseResponse.setError(false);
            if (entity == null) {
                baseResponse.setData(null);
                baseResponse.setMessage("No Shops");
            } else {
                baseResponse.setData(entity.get());
                baseResponse.setMessage("Shop Details");
            }

            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
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

    @GetMapping(value = "/get/{id}/menu")
    public @ResponseBody
    ResponseEntity<String> getMenusbyShopid(@PathVariable("id") long id,
                                            @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }

            final List<MenuEntity> list = shopService.getMenuesByShop(id);
            baseResponse.setError(false);
            if (list == null) {
                baseResponse.setData(null);
                baseResponse.setMessage("No menus");
            } else {
                baseResponse.setData(list);
                baseResponse.setMessage("Shop Details");
            }

            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
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


    @PostMapping(value = "/queue/create")
    public @ResponseBody
    ResponseEntity<String> createQueue(@RequestBody CreateQueue CreateQueue,
                                       @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue()) || byToken.getRole().equals(UserRoleEnum.SHOP_OPERATOR.getValue())) {


                QueueEntity queueEntity = new QueueEntity();
                Optional<ShopEntity> shop = shopService.getShop(CreateQueue.getShopid());
                if (shop.get() != null) {
                    queueEntity.setShop(shop.get());
                    queueEntity.setMaxsize(CreateQueue.getMax());
                } else {
                    baseResponse.setError(true);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Shop is not valid");
                    baseResponse.setCode(500);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

                }
                QueueEntity save = shopService.CreateQueue(queueEntity);
                baseResponse.setError(false);
                baseResponse.setData(save);
                baseResponse.setMessage("Created Queue Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You haven't Permission");
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

    @PutMapping(value = "/queue/update")
    public @ResponseBody
    ResponseEntity<String> UpdateQueue(@RequestBody CreateQueue CreateQueue,
                                       @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue()) || byToken.getRole().equals(UserRoleEnum.SHOP_OPERATOR.getValue())) {


                QueueEntity queueEntity = new QueueEntity();
                Optional<ShopEntity> shop = shopService.getShop(CreateQueue.getShopid());
                if (shop.get() != null) {
                    queueEntity.setId(CreateQueue.getId());
                    queueEntity.setShop(shop.get());
                    queueEntity.setMaxsize(CreateQueue.getMax());
                } else {
                    baseResponse.setError(true);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Shop is not valid");
                    baseResponse.setCode(500);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

                }
                QueueEntity save = shopService.UpdateQueue(queueEntity);
                baseResponse.setError(false);
                baseResponse.setData(save);
                baseResponse.setMessage("Update Queue Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You haven't Permission");
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

    @DeleteMapping(value = "/queue/delete")
    public @ResponseBody
    ResponseEntity<String> DeleteQueue(@RequestBody CreateQueue CreateQueue,
                                       @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue()) || byToken.getRole().equals(UserRoleEnum.SHOP_OPERATOR.getValue())) {

                QueueEntity queueEntity = new QueueEntity();
                Optional<ShopEntity> shop = shopService.getShop(CreateQueue.getShopid());
                if (shop.get() != null) {
                    queueEntity.setShop(shop.get());
                    queueEntity.setMaxsize(CreateQueue.getMax());
                    queueEntity.setId(CreateQueue.getId());
                } else {
                    baseResponse.setError(true);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Shop is not valid");
                    baseResponse.setCode(500);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

                }
                QueueEntity save = shopService.DeleteQueue(queueEntity);
                baseResponse.setError(false);
                baseResponse.setData(save);
                baseResponse.setMessage("Delete Queue Successfully");
                baseResponse.setCode(201);
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You haven't Permission");
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

    @PostMapping(value = "/create/subuser")
    public @ResponseBody
    ResponseEntity<String> createSubUser(@RequestBody CreateUser createUser,
                                         @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            String s[] = Authorization.split(" ");
            final UserEntity byToken = userRepository.findByToken(s[1]);
            if (byToken.getRole().equals(UserRoleEnum.SHOP_OWNER.getValue())) {

                UserEntity userEntity = new UserEntity();
                final Optional<ShopEntity> shop = shopService.getShop(createUser.getShopid());
                if (shop.get() != null) {
                    userEntity.setShop(shop.get());
                    userEntity.setUserName(createUser.getUserName());
                    userEntity.setRole(createUser.getRole());
                    userEntity.setPassword(createUser.getPassword());
                    final UserEntity userEntity1 = shopService.CreateUser(userEntity);

                    baseResponse.setError(false);
                    baseResponse.setData(userEntity1);
                    baseResponse.setMessage("User Create Successfully");
                    baseResponse.setCode(201);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);
                } else {
                    baseResponse.setError(true);
                    baseResponse.setData(null);
                    baseResponse.setMessage("Shop is not valid");
                    baseResponse.setCode(500);
                    responseJson = mapper.writeValueAsString(baseResponse);
                    return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

                }

            } else {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("You Havent permission");
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

    @GetMapping(value = "/get/{id}/orders/{status}")
    public @ResponseBody
    ResponseEntity<String> getshoporders(@PathVariable("id") long id,
                                         @PathVariable("status") String status,
                                         @RequestHeader("Authorization") String Authorization) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        BaseRestResponse baseResponse = new BaseRestResponse();
        String responseJson = "";
        try {
            boolean Auth = validateToken(Authorization);
            if (!Auth) {
                baseResponse.setError(true);
                baseResponse.setData(null);
                baseResponse.setMessage("Authentication Fail");
                responseJson = mapper.writeValueAsString(baseResponse);
                return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            List<OrderEntity> filtered = new ArrayList<>();
            List<OrderEntity> shopOrders = shopService.getShopOrders(id);
            for (OrderEntity orderEntity : shopOrders) {
                if (orderEntity.getOrderStatusEnum().equals(status.toUpperCase())){
                    filtered.add(orderEntity);
                }
            }
            baseResponse.setError(false);
            baseResponse.setData(filtered);
            baseResponse.setMessage("Shop orders");
            baseResponse.setCode(201);
            responseJson = mapper.writeValueAsString(baseResponse);
            return new ResponseEntity<String>(responseJson, responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
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
