package com.zkart.screens.userLogin;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

public class LoginView extends BaseScreen {
    @Override
    public void display()  {
        try {
            header("User Login");
                String email = getString("Enter Email :");
                String password = getString("Enter password :");

                if (ZkartRepository.validateUser(email, password)) {
                    alert("Login Successful");
                }
        }catch (InvalidCredentialsException e){
            alert("Invalid Email or Password");
            if (getBoolean("Do you want to try again ?")) {
                display();
            }
        }
    }
}
