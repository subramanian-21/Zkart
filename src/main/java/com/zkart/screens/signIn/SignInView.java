package com.zkart.screens.signIn;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;
import com.zkart.utils.exceptions.TerminatePageInterupt;

import java.util.EnumMap;

/**
 *
 *  string email = 2;
 *   string fullname = 4;
 *   string password = 5;
 *   int32 totalTransaction = 6;
 *   int32 transactionCount = 7;
 *   repeated string prePasswords = 8;
 */
public class SignInView extends BaseScreen {
    @Override
    public void display() {
        try {
            header("User Sign In");

            if(getBoolean("Do You already have an account ?")) {
                navigate(new LoginView());
                return;
            }
            String name = getString("Enter Full Name :");
            String email = getEmailInput();
            String passwd = getPasswordInput();
            while(true) {
                String confirmPassword = getString("Confirm Password :");
                if(!passwd.equals(PasswordHandler.encryptPassword(confirmPassword))) {
                    System.out.println("Password Not Matching.");
                    if(!getBoolean("Do you want to Enter again ?")){
                        return;
                    }
                }else {
                    break;
                }
            }
            ZkartRepository.userSignIn(name, email, passwd);
            alert("Account creation Successful");
        } catch (TerminatePageInterupt i){

        }
    }
    public String getEmailInput() throws TerminatePageInterupt{
        String email = getString("Enter Email :");
        if(!ZkartRepository.signInEmailValidation(email)){
            System.out.println("Email Id Already Exists.");
            if(getBoolean("Do you want to Login ?")){
                navigate(new LoginView());
            }
            if(getBoolean("Do you want to try again ?")){
                return getEmailInput();
            }
            throw new TerminatePageInterupt();
        }
        return email;
    }
    public String getPasswordInput() throws TerminatePageInterupt{

        String passwd = getString("Enter password :");
        try{
            PasswordHandler.validatePassword(passwd);
            return passwd;
        }catch (InvalidPasswordSyntaxException e){
            System.out.println("Invalid Password Syntax");
            System.out.println(PasswordHandler.getValidPasswordSyntax());
            if(getBoolean("Do you want to try again ?")){
                return getPasswordInput();
            }
        }
        throw new TerminatePageInterupt();
    }
}
