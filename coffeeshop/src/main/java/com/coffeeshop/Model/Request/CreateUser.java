package com.coffeeshop.Model.Request;

import lombok.Data;

@Data
public class CreateUser {
    private Long id;
    private String userName;
    private String password;
    private String role; //SHOP_OWNER,SHOP_OPERATOR
    private String token;
    private long shopid;
}
