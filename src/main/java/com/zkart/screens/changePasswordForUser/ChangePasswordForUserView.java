package com.zkart.screens.changePasswordForUser;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.adminLogin.AdminLoginView;
import com.zkart.screens.changePassword.ChangePasswordViewModel;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;
import com.zkart.utils.exceptions.TerminatePageInterupt;

public class ChangePasswordForUserView extends BaseScreen {
    private ChangePasswordForUserViewModel viewModel;
    public ChangePasswordForUserView (){
        viewModel = new ChangePasswordForUserViewModel();
    }
    @Override
    public void display() {
        System.out.println("Page Not Found");
    }
    public String getPasswords(String password) throws TerminatePageInterupt {
        String currentPassword = getString("Enter Current Password :");
        if(!viewModel.validatePasswords(currentPassword,password)){
            alert("Access Denied");
            throw new TerminatePageInterupt();
        }
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
            if(viewModel.validatePasswords(newPassword,confirmPassword)) {
                return newPassword;
            }else {
                alert("Password Doesnt Match");
                if(!getBoolean("Do you want to try again ?")){
                    throw new TerminatePageInterupt();
                }
            }
        }
    }
    public String getPasswords() throws TerminatePageInterupt{
        ZkartRepository.isUserLogin = false;
        navigate(new LoginView());
        if(!ZkartRepository.isUserLogin) {
            throw new TerminatePageInterupt();
        }
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
            if(viewModel.validatePasswords(newPassword,confirmPassword)) {
                return newPassword;
            }else {
                alert("Password Doesnt Match");
                if(!getBoolean("Do you want to try again ?")){
                    throw new TerminatePageInterupt();
                }
            }
        }
    }

}
