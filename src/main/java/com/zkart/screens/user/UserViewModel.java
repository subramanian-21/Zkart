package com.zkart.screens.user;
public class UserViewModel {
    public boolean validatePasswords(String current, String currentPassword) {
        return current.equals(currentPassword);
    }
}
