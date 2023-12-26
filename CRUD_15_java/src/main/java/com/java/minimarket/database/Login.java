package com.java.minimarket.database;

import java.util.Scanner;

public class Login {
     private User user;
    private Captcha captcha;

    public Login(User user) {
        this.user = user;
        this.captcha = new Captcha();
    }
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("username : ");
        String username = scanner.nextLine();

        System.out.print("Password :");
        String password = scanner.nextLine();

        if (user.check(username, password)) {
            System.out.println("Masukkan captcha berikut: " + captcha.getCaptcha());
            String captchaInput = scanner.nextLine();

            if (captcha.verifyCaptcha(captchaInput)) {
                System.out.println("Login berhasil! \n");
                return true;
            }
        }
        return false;
    }

}


