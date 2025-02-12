package com.zkart.screens.admin;

import com.zkart.model.AdminProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

import java.util.List;

public class AdminViewModel {
    public boolean validateAdmin(String email, String password) throws InitialAdminLoginException, InvalidCredentialsException {
        return ZkartRepository.validateAdmin(email, password);
    }
    public boolean validatePasswords(String current, String currentPassword) {
        return current.equals(currentPassword);
    }
    public boolean changePassword(String password) {
        return ZkartRepository.updateAdminPassword(password);
    }
    public boolean validateAndChangePassword(String password) throws InvalidPasswordSyntaxException {
        if(!PasswordHandler.validatePassword(password)){
            return false;
        }
        AdminProto.Admin admin = ZkartRepository.admin;
        List<String> list = admin.getAdminUser().getPrePasswordsList();
        int start = Math.max(0, list.size() - 3);
        int end = list.size();

        for(int i = start;i<end;i++) {
            if(PasswordHandler.encryptPassword(password).equals(list.get(i))) {
                return false;
            }
        }
        return ZkartRepository.updateAdminPassword(password);
    }
}
