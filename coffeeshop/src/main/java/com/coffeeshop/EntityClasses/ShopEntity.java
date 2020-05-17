package com.coffeeshop.EntityClasses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "shop")
@ApiModel(description = "Create ShopEntity Details")
public class ShopEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;

    public ShopEntity() {
    }

    @Column(name="shopName")
    @ApiModelProperty(notes = "Shop Name")
    private String shopName;

    @Column(name="shopAddress")
    @ApiModelProperty(notes = "Shop Address")
    private String shopAddress;

    @Column(name="phoneNumber")
    @ApiModelProperty(notes = "Phone Number")
    private String phoneNumber;

    @Column(name="latitude")
    @ApiModelProperty(notes = "Latitude")
    private String latitude;

    @Column(name="longitude")
    @ApiModelProperty(notes = "Longitude")
    private String longitude;

    @Column(name="opentime")
    @ApiModelProperty(notes = "Open Time")
    private String opentime;

    @Column(name="closetime")
    @ApiModelProperty(notes = "Close Time")
    private String closetime;


}