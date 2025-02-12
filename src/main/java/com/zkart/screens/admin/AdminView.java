package com.zkart.screens.admin;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;
import com.zkart.utils.exceptions.TerminatePageInterupt;

public class AdminView extends BaseScreen {
    private static AdminView instance;
    private AdminViewModel viewModel;
    private AdminView(){
        viewModel = new AdminViewModel();
    }
    public static AdminView getInstance(){
        if(instance == null) {
            instance = new AdminView();
        }
        return instance;
    }
    // login
    public void adminLogin() throws TerminatePageInterupt {
        header("Admin Login");
        String email = getString("Enter Admin Email : ");
        String password = getString("Enter Admin Password :");
        try {
            viewModel.validateAdmin(email, password);
            alert("Login successful");
        }catch (InitialAdminLoginException e){
            alert(e.getMessage());
            changePassword();
        }catch (InvalidCredentialsException e) {
            alert(e.getMessage());
            if(getBoolean("Do you want to try again ? ")){
                display();
            }
            throw new TerminatePageInterupt();
        }
    }
    // change password

    public void changePassword() {
        try {
            if(!ZkartRepository.isAdminLogin) {
                adminLogin();
            }
            if(!ZkartRepository.isAdminLogin) {
                throw new TerminatePageInterupt();
            }
            String newPassword = getPasswords();
            if(viewModel.validateAndChangePassword(newPassword)){
                alert("Successfully changed password");
            }else {
                alert("Password validation failed");
                System.out.println("* password must not be same as the last three passwords.");
            }
        }catch (InvalidPasswordSyntaxException | TerminatePageInterupt e){
            alert(e.getMessage());
            if(getBoolean("Do you want to try again ?")){
                changePassword();
            }
        }
    }
    public String getPasswords() throws TerminatePageInterupt{
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
