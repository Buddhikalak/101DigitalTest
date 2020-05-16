/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.EntityClasses;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class MenuEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public MenuEntity() {
    }

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id")
    private ShopEntity Shop;
}
