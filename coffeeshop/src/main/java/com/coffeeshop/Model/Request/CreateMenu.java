package com.coffeeshop.Model.Request;

import lombok.Data;

@Data
public class CreateMenu {
    private long shopid;
    private String name;
}