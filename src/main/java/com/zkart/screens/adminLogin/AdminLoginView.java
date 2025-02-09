package com.zkart.screens.adminLogin;

import com.zkart.model.AdminProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.changePassword.ChangePasswordView;
import com.zkart.screens.changePassword.ChangePasswordViewModel;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.TerminatePageInterupt;

import java.net.Socket;

public class AdminLoginView extends BaseScreen {
    private AdminLoginViewModel viewModel;
    public AdminLoginView () {
        viewModel = new AdminLoginViewModel();
    }
    @Override
    public void display() {
        header("Admin Login");
        String email = getString("Enter Admin Email : ");
        String password = getString("Enter Admin Password :");
        try {
            viewModel.validateAdmin(email, password);
            alert("Login successful");
        }catch (InitialAdminLoginException e){
            alert(e.getMessage());
            try {
                ZkartRepository.updateAdminPassword(new ChangePasswordView().getPasswords(password));
                alert("Password Changed Successfully");
            }catch (TerminatePageInterupt e1) {
                alert(e1.getMessage());
            }
        }catch (InvalidCredentialsException e) {
            alert(e.getMessage());
            if(getBoolean("Do you want to try again ? ")){
                display();
            }
        }
    }

}
