/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.coffeeshop.Enum;

public enum  OrderStatusEnum {

    PENDING(1, "PENDING"),
    PREPARED(2, "PREPARED"),
    COMPLETED(3, "COMPLETED"),
    CANCELLED(4, "CANCELLED");

    private int key;
    private String value;

    OrderStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static OrderStatusEnum findValue(int key) {
        for (OrderStatusEnum v : values()) {
            if (v.getKey() == key) {
                return v;
            }
        }
        return null;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
}
