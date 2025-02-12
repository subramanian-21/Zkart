package com.zkart.utils;

import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

import java.sql.Timestamp;
import java.time.Instant;

public class PasswordHandler {

    public static String encryptPassword(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        for(int i = 0;i<password.length();i++) {
            char ch = password.charAt(i);
            encryptedPassword.append(handlePasswordCharacter(ch, true));
        }
        return encryptedPassword.toString();
    }
    public static String decryptPassword(String hash) {
        StringBuilder password = new StringBuilder();
        for (int i = 0;i<hash.length();i++) {
            char ch = hash.charAt(i);
            if(isValidCharacter(ch) != 0)
             password.append(handlePasswordCharacter(ch, false));
            else return null;
        }
        return password.toString();
    }

    private static char handlePasswordCharacter(char ch, boolean encrypt) {
        if(encrypt) {
           if(ch != 'z' && ch != 'Z' && ch != '9') {
               ch += 1;
           }else {
               if(ch == 'z') {
                   ch = 'a';
               }else if(ch == 'Z') {
                   ch = 'A';
               }else {
                   ch = 0;
               }
           }
        }else {
            if(ch != 'a' && ch != 'A' && ch != '0') {
                ch -= 1;
            }else {
                if(ch == 'a') {
                    ch = 'Z';
                }else if(ch == 'A') {
                    ch = 'a';
                }else {
                    ch = 9;
                }
            }
        }
        return ch;
    }

    private static int isValidCharacter(char ch) {
        if (ch >='a' && ch <= 'z' ) {
            return 1;
        }else if(ch >= 'A' && ch <= 'Z') {
            return 2;
        }else if (ch >= '0' && ch <= '9') {
            return 3;
        }
        return 0;
    }
    public static boolean validatePassword(String password) throws InvalidPasswordSyntaxException{
        int lowerCount = 0;
        int upperCount = 0;
        int numberCount = 0;
        for(int i = 0;i<password.length();i++) {

            char ch = password.charAt(i);
            int passwdGroup = isValidCharacter(ch);
            if(passwdGroup == 0) {
                throw new InvalidPasswordSyntaxException();
            }
            if(passwdGroup == 1) {
                lowerCount++;
            }else if(passwdGroup == 2) {
                upperCount++;
            }else if(passwdGroup == 3) {
                numberCount++;
            }
        }
        if(lowerCount < 2 || upperCount < 2 || numberCount < 2) {
            throw new InvalidPasswordSyntaxException();
        }
        return true;
    }
    public static String getValidPasswordSyntax() {
        return "* Password must contain 2 or more Upper Case\n"+
                "* Password must contain 2 or more Lower Case\n"+
                "* Password must contain 2 or more Numericals\n"+
                "* No Special Characters are allowed\n";
    }

}
