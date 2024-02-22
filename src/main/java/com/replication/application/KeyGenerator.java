package com.replication.application;

public class KeyGenerator {

    private static final String MEMBER_KEY = "member";

    public static String memberKeyGenerate(String loginId) {
        return MEMBER_KEY + " : " + loginId;
    }
}
