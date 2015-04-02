package com.gamebox.util;

import java.util.UUID;


public class OrderUtils {
    public static String getOrderSn() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
