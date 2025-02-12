package com.zkart.screens;

import com.zkart.model.BaseUserProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;
import com.zkart.utils.exceptions.TerminatePageInterupt;

import java.util.List;

public abstract class BaseUserView extends BaseScreen {
    public void login(boolean isUser) throws TerminatePageInterupt{
        if(isUser){
            header("User Login");
        }else {
            header("Admin Login");
        }
        String email = getString("Enter Email :");
        String password = getString("Enter password :");
        if(isUser) {
            try {
                if (ZkartRepository.validateUser(email, password)) {
                    alert("Login Successful");
                }
            }catch (InvalidCredentialsException e){
                alert("Invalid Email or Password");
                if (getBoolean("Do you want to try again ?")) {
                    login(isUser);
                }
            }
        }
        else {
            try {
                ZkartRepository.validateAdmin(email, password);
                alert("Login successful");
            }catch (InitialAdminLoginException e){
                alert(e.getMessage());
                changePassword(isUser);
            }catch (InvalidCredentialsException e) {
                alert(e.getMessage());
                if(getBoolean("Do you want to try again ? ")){
                    login(isUser);
                }
                throw new TerminatePageInterupt();
            }
        }
    }
    public void changePassword(boolean isUser) {
        try {
            if(isUser && !ZkartRepository.isUserLogin) {
                login(isUser);
            }else if(!isUser && !ZkartRepository.isAdminLogin) {
                login(isUser);
            }

            if(isUser && !ZkartRepository.isUserLogin) {
                throw new TerminatePageInterupt();
            }
            else if(!isUser && !ZkartRepository.isAdminLogin){
                throw new TerminatePageInterupt();
            }
            String newPassword = getPasswords();
            if(isUser && validateAndChangePassword(newPassword, isUser)) {
                alert("Successfully changed password");
            }else if (!isUser && validateAndChangePassword(newPassword, isUser)) {
                alert("Successfully changed password");
            }
            else {
                alert("Password validation failed");
                System.out.println("* password must not be same as the last three passwords.");
            }
        }catch (InvalidPasswordSyntaxException | TerminatePageInterupt e) {
            throw new RuntimeException(e);
        }
    }
    public String getPasswords() throws TerminatePageInterupt {
        String newPassword = null;
        while (true) {
            try {
                newPassword = getString("Enter New Password :");
                if(PasswordHandler.validatePassword(newPassword)){
                    break;
                }
            }catch (InvalidPasswordSyntaxException e) {
                System.out.println("Invalid Password Syntax");
                System.out.println(PasswordHandler.getValidPasswordSyntax());
                if(!getBoolean("Do you want to try again ?")) {
                    throw new TerminatePageInterupt();
                }
            }
        }

        while (true) {
            String confirmPassword = getString("Confirm New Password :");
            if(newPassword.equals(confirmPassword)) {
                return newPassword;
            }else {
                alert("Password Doesnt Match");
                if(!getBoolean("Do you want to try again ?")){
                    throw new TerminatePageInterupt();
                }
            }
        }
    }
    public boolean validateAndChangePassword(String password, boolean isUser) throws InvalidPasswordSyntaxException {
        if(!PasswordHandler.validatePassword(password)){
            return false;
        }
        BaseUserProto.BaseUser user = null;
        if(isUser) {
            user = ZkartRepository.loggedInUser.getUserDetails();
        }else {
            user = ZkartRepository.admin.getAdminUser();
        }
        List<String> list = user.getPrePasswordsList();
        int start = Math.max(0, list.size() - 3);
        int end = list.size();

        for(int i = start;i<end;i++) {
            if(PasswordHandler.encryptPassword(password).equals(list.get(i))) {
                return false;
            }
        }
        return ZkartRepository.updateUserPassword(password, isUser);
    }

}
