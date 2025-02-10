package com.zkart.screens.changePasswordForUser;

public class ChangePasswordForUserViewModel {
    public boolean validatePasswords(String current, String currentPassword) {
        return current.equals(currentPassword);
    }
}
