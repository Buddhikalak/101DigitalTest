package com.coffeeshop.Enum;

public enum UserRoleEnum {
    SHOP_OWNER(1, "SHOP_OWNER"),
    SHOP_OPERATOR(2, "SHOP_OPERATOR");

    private int key;
    private String value;

    UserRoleEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static UserRoleEnum findValue(int key) {
        for (UserRoleEnum v : values()) {
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


