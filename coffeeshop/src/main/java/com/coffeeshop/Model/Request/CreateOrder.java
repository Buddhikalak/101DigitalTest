/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Model.Request;

import com.coffeeshop.EntityClasses.CustomerEntity;
import com.coffeeshop.EntityClasses.MenuEntity;
import com.coffeeshop.EntityClasses.ShopEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@ApiModel(description = "Create Order Details")
public class CreateOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;

    @ApiModelProperty(notes = "Shop Id")
    private long Shopid;

    @ApiModelProperty(notes = "Menu Id")
    private long menuid;

    @ApiModelProperty(notes = "User Id")
    private long userid;

    @ApiModelProperty(notes = "Latitude")
    private String latitude;

    @ApiModelProperty(notes = "Longitude")
    private String Longitude;

    @ApiModelProperty(notes = "Queue Number-Automatically Selected")
    private int queue;

    @ApiModelProperty(notes = "Automatically Update the Date")
    private Timestamp date;

    @ApiModelProperty(notes = "Automatically Update the Status")
    private String orderStatusEnum;
}
