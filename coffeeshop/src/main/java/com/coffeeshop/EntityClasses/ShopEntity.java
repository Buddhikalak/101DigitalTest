package com.coffeeshop.EntityClasses;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "shop")
public class ShopEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ShopEntity() {
    }

    @Override
    public String toString() {
        return "ShopEntity{" +
                "id=" + id +
                ", name='" + shopName + '\'' +
                ", address='" + shopAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Column(name="shopName")
    private String shopName;

    @Column(name="shopAddress")
    private String shopAddress;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @Column(name="latitude")
    private String latitude;

    @Column(name="longitude")
    private String longitude;

}