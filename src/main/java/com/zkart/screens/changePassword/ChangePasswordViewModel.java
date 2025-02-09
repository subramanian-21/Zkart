package com.zkart.screens.changePassword;

public class ChangePasswordViewModel {
    public boolean validatePasswords(String current, String currentPassword) {
        return current.equals(currentPassword);
    }
}
