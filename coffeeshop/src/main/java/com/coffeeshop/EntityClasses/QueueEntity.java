package com.coffeeshop.EntityClasses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@ApiModel(description = "Create QueueEntity Details")
public class QueueEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Database Generated Id/PrimaryKey")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop")
    @ApiModelProperty(notes = "Shop Id")
    private ShopEntity Shop;

    @ApiModelProperty(notes = "Max Size")
    private int maxsize;

}
