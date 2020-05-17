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
import java.sql.Timestamp;

@Data
@Entity
@ApiModel(description = "Create Customer Request Details")
public class CustomerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;

    @Column(name="name")
    @ApiModelProperty(notes = "CustomerName")
    private String name;

    @Column(name="mobile")
    @ApiModelProperty(notes = "Mobile Number")
    private String mobile;

    @Column(name="password")
    @ApiModelProperty(notes = "Password")
    private String password;

    @Column(name="username")
    @ApiModelProperty(notes = "UserName")
    private String username;

    @Column(name="address")
    private String address;

    @Column(name="token")
    @ApiModelProperty(notes = "Address")
    private String token;

    @Column(name="date")
    @ApiModelProperty(notes = "CusReg Date")
    private Timestamp timestamp;

}
