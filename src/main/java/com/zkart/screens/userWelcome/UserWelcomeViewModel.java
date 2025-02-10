package com.zkart.screens.userWelcome;


import com.zkart.model.UserProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

import java.util.List;

public class UserWelcomeViewModel {
    public boolean validateAndChangePassword(String password) throws InvalidPasswordSyntaxException {
        if(!PasswordHandler.validatePassword(password)){
            return false;
        }
        UserProto.User user = ZkartRepository.loggedInUser;
        List<String> list = user.getPrePasswordsList();
        int start = Math.max(0, list.size() - 3);
        int end = list.size();

        for(int i = start;i<end;i++) {
            if(PasswordHandler.encryptPassword(password).equals(list.get(i))) {
                return false;
            }
        }
        return ZkartRepository.updateUserPassword(password);
    }
}
