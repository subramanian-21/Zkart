package com.zkart.screens.user;

import com.zkart.model.CouponProto;
import com.zkart.model.OrderProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.order.OrderView;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;
import com.zkart.utils.exceptions.TerminatePageInterupt;

import java.util.List;

public class UserView extends BaseScreen {
    public static UserView instance;
    private UserViewModel viewModel;
    private UserView(){
        viewModel = new UserViewModel();
    }
    public static UserView getInstance(){
        if(instance == null) {
            instance = new UserView();
        }
        return instance;
    }

    // login
    public void login(){
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
                login();
            }
        }
    }

    public void signUp(){
        try {
            header("User Sign Up");

            if(getBoolean("Do You already have an account ?")) {
                login();
                return;
            }
            String name = getString("Enter Full Name :");
            String email = getEmailInput();
            String passwd = getPasswordInput();
            while(true) {
                String confirmPassword = getString("Confirm Password :");
                if(!passwd.equals(confirmPassword)) {
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
            alert(i.getMessage());
        }
    }
    public String getEmailInput() throws TerminatePageInterupt{
        String email = getString("Enter Email :");
        if(!ZkartRepository.signInEmailValidation(email)){
            System.out.println("Email Id Already Exists.");
            if(getBoolean("Do you want to Login ?")){
                login();
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

    // change password
    public void changePassword(){
        try {
            if(!ZkartRepository.isUserLogin) {
                login();
            }
            if(!ZkartRepository.isUserLogin) {
                throw new TerminatePageInterupt();
            }
            String newPassword = getPasswords();
            if(viewModel.validateAndChangePassword(newPassword)){
                alert("Successfully changed password");
            }else {
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

    // my orders

    public void myOrders() {

        try {
            if(!ZkartRepository.isUserLogin) {
               login();
            }
            if(ZkartRepository.isUserLogin) {
                header("My Orders");
                List<OrderProto.Order> orders = ZkartRepository.getUserOrders(ZkartRepository.loggedInUser.getUserDetails().getId());
                if (orders.isEmpty()) {
                    alert("No Orders Found");
                    return;
                }
                for (OrderProto.Order order : orders) {
                    OrderView.printOrders(order);
                }
            }
        }catch (Exception e) {

        }
    }


    // my coupons

    public void myCoupons() {
        if(!ZkartRepository.isUserLogin) {
            login();
        }
        if(ZkartRepository.isUserLogin) {
            header("My Coupons");

            // show available coupons;
            System.out.println("---------------------------------------------");

            List<CouponProto.Coupon> couponList = ZkartRepository.getUserCoupons();
            if(couponList.isEmpty()) {
                alert("No coupons Available");
                return;
            }
            for(var coupon : couponList) {
                OrderView.printCoupon(coupon);
            }
            System.out.println("---------------------------------------------");
        }
    }

}
