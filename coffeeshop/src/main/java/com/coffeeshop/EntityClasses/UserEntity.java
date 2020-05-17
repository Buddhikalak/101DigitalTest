package com.coffeeshop.EntityClasses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@ApiModel(description = "Create UserEntity Details")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;

    @ApiModelProperty(notes = "User Name")
    private String userName;

    @ApiModelProperty(notes = "Password")
    private String password;

    @ApiModelProperty(notes = "Role")
    private String role; //SHOP_OWNER,SHOP_OPERATOR

    @ApiModelProperty(notes = "Database Generated Token")
    private String token;

    @ManyToOne
    @JoinColumn(name = "shopid")
    @ApiModelProperty(notes = "Shop Id")
    private ShopEntity Shop;

}
