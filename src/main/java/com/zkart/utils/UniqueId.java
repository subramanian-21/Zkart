package com.zkart.utils;

import java.util.UUID;

public class UniqueId {
    public static String getCouponId (int userId){
        return UUID.randomUUID().toString().substring(0, 5)+userId;
    }
    public static int generateRandomInt(int start, int end) throws IllegalArgumentException{
        if(start > end) {
            throw new IllegalArgumentException("Start should be lesser than end");
        }
        return start + (int)Math.floor(Math.random() * (end - start +1));
    }
}
