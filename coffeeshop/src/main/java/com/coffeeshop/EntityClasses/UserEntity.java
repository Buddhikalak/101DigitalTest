package com.coffeeshop.EntityClasses;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String password;
    private String role; //SHOP_OWNER,SHOP_OPERATOR
    private String token;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private ShopEntity Shop;

}
