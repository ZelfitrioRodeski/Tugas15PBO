package com.java.minimarket.database;

import java.util.Random;

public class Captcha {
    private String captcha;

    public Captcha() {
        this.captcha = generateCaptcha();
    }

    private String generateCaptcha() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 6; i++) { 
            int index = rand.nextInt(alphaNumericString.length());
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public boolean verifyCaptcha(String input) {
        return captcha.equals(input); 
    }

    public String getCaptcha() {
        return captcha;
    }
}
