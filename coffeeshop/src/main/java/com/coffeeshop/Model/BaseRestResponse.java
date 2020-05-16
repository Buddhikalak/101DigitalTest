package com.coffeeshop.Model;

import lombok.Data;

@Data
public class BaseRestResponse {
    private Boolean error;
    private String message;
    private Object data;
    private int code;
}
