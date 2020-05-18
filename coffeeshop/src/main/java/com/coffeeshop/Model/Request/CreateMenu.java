package com.coffeeshop.Model.Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "All details about Create Menu Request Details ")
public class CreateMenu {
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private long id;
    @ApiModelProperty(notes = "Database Generated Shopid")
    private long shopid;
    @ApiModelProperty(notes = "Menu Name")
    private String name;
    @ApiModelProperty(notes = "Price")
    private double price;
}
