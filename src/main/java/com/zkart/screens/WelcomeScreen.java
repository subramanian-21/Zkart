package com.zkart.screens;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.addProducts.AddProductsView;
import com.zkart.screens.adminLogin.AdminLoginView;
import com.zkart.screens.changePassword.ChangePasswordView;
import com.zkart.screens.myOrders.MyOrdersView;
import com.zkart.screens.orderProduct.OrderProduct;
import com.zkart.screens.signIn.SignInView;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.exceptions.TerminatePageInterupt;

public class WelcomeScreen extends BaseScreen {
    @Override
    public void display() {
        try {
            while (true) {

                if(getBoolean("Are you a User ?")){
                    while (true) {
                        header("Welcome to Z-Kart");
                        if(!ZkartRepository.isUserLogin) {
                            System.out.println("1 -> User Login");
                        }
                        System.out.println("2 -> User Sign up");
                        System.out.println("3 -> Search Product");
                        System.out.println("4 -> Order Product");
                        if(ZkartRepository.isUserLogin) {
                            System.out.println("5 -> My Orders");
                        }

                        System.out.println("6 -> Back");
                        System.out.println("7 -> Exit");
                        int opt = getInt("Choose Option :");

                        if(opt == 1) {
                            navigate(new LoginView());

                        }else if(opt == 2){
                            navigate(new SignInView());

                        }else if(opt == 3){
                            navigate(new ViewProducts());
                        }else if(opt == 4){
                            navigate(new OrderProduct());
                        }
                        else if(opt == 5){
                            navigate(new MyOrdersView());
                        }else if(opt == 6) {
                            ZkartRepository.logout();
                            break;
                        }else if(opt == 7) {
                            alert("Thank you visit again.");
                            return;
                        }else {
                            System.out.println("Invalid Option");
                            if(!getBoolean("Do you want to continue ?")) {
                                break;
                            }
                        }

                    }
                }else {
                    while (true) {
                        header("Welcome to Z-Kart Admin");
                        if(!ZkartRepository.isAdminLogin){
                            System.out.println("1 -> Admin Login");
                        }
                        System.out.println("2 -> Add Product");
                        System.out.println("3 -> Admin password Update");
                        System.out.println("4 -> Back");
                        System.out.println("5 -> Exit");

                        int opt = getInt("Enter Option :");
                        if(opt == 1 && !ZkartRepository.isAdminLogin) {
                            navigate(new AdminLoginView());
                        }else if(opt == 2) {
                            navigate(new AddProductsView());
                        }else if(opt == 3) {
                            ZkartRepository.updateAdminPassword(new ChangePasswordView().getPasswords());
                        }else if(opt == 4){
                            ZkartRepository.logout();
                           break;
                        }else if(opt == 5){
                            return;
                        }else {
                            System.out.println("Invalid Option");
                            if(!getBoolean("Do you want to continue ?")) {
                                break;
                            }
                        }
                    }

                }
            }
        }catch (TerminatePageInterupt e) {
            display();
        }
    }
}
