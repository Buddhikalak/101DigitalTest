/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.EntityClasses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@ApiModel(description = "Create MenuEntity Details")
public class MenuEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;

    public MenuEntity() {
    }

    @Column(name="name")
    @ApiModelProperty(notes = "Menu Name")
    private String name;

    @Column(name="price")
    @ApiModelProperty(notes = "Price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "shopid")
    @ApiModelProperty(notes = "Shop Id")
    private ShopEntity Shop;
}
