package com.zkart.screens.user;

import com.zkart.model.UserProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

import java.util.List;

public class UserViewModel {

    public boolean validatePasswords(String current, String currentPassword) {
        return current.equals(currentPassword);
    }
}
