/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Model.Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "All details about Create QueueRequest Details ")
public class CreateQueue {
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private long id;

    @ApiModelProperty(notes = "Database Generated Shopid")
    private long shopid;

    @ApiModelProperty(notes = "Max number of queue")
    private int max;

}
