package com.zkart.screens.adminLogin;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;

public class AdminLoginViewModel {
    public boolean validateAdmin(String email, String password) throws InitialAdminLoginException, InvalidCredentialsException {
        return ZkartRepository.validateAdmin(email, password);
    }

}
