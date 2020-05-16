package com.coffeeshop.EntityClasses;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class QueueEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private ShopEntity Shop;

    private int maxsize;
}
