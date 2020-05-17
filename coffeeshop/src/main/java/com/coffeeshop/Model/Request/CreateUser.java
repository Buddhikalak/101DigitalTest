package com.coffeeshop.Model.Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "All Request Details about Create User  ")
public class CreateUser {
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;
    @ApiModelProperty(notes = "UserName")
    private String userName;
    @ApiModelProperty(notes = "Password")
    private String password;
    @ApiModelProperty(notes = "UserRole")
    private String role; //SHOP_OWNER,SHOP_OPERATOR
    @ApiModelProperty(notes = "Token,Its Automatically generate After Login")
    private String token;
    @ApiModelProperty(notes = "Perticular Shop id")
    private long shopid;
}
