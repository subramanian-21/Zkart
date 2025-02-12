package com.zkart.screens;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.admin.AdminView;
import com.zkart.screens.order.OrderView;
import com.zkart.screens.product.ProductView;
import com.zkart.screens.user.UserView;
import com.zkart.utils.BaseScreen;

public class WelcomeScreenView extends BaseScreen {
    public void display(){
        header("Z-kart Application");
        while (true) {
            if(getBoolean("Are you a user ?")){
                userScreenView();
            }else {
                adminScreenView();
            }
        }
    }
    public void userScreenView () {
        while (true) {
            header("Welcome to Z-Kart");
            if (!ZkartRepository.isUserLogin) {
                System.out.println("1 -> User Login");
                System.out.println("2 -> User Sign up");
            }
            System.out.println("3 -> Search Product");
            System.out.println("4 -> Order Product");
            if (ZkartRepository.isUserLogin) {
                System.out.println("5 -> My Orders");
                System.out.println("6 -> My Coupons");
                System.out.println("7 -> Logout");
                System.out.println("8 -> Change Password");
            }
            System.out.println("9 -> Back");
            System.out.println("10 -> Exit");
            int opt = getInt("Choose Option :");

            if (opt == 1 && !ZkartRepository.isUserLogin) {
                UserView.getInstance().login();
            } else if (opt == 2 && !ZkartRepository.isUserLogin) {
                UserView.getInstance().signUp();
                UserView.getInstance().login();
            } else if (opt == 3) {
                ProductView.getInstance().userDisplay();
            } else if (opt == 4) {
                OrderView.getInstance().userDisplay();
            } else if (opt == 5 && ZkartRepository.isUserLogin) {
                UserView.getInstance().myOrders();
            } else if (opt == 6 && ZkartRepository.isUserLogin) {
                UserView.getInstance().myCoupons();
            } else if (opt == 7 && ZkartRepository.isUserLogin) {
                ZkartRepository.logout();
            } else if (opt == 8 && ZkartRepository.isUserLogin) {
                UserView.getInstance().changePassword();
            } else if (opt == 9) {
                break;
            } else if (opt == 10) {
                alert("Thank you visit again.");
                return;
            } else {
                System.out.println("Invalid Option");
                if (!getBoolean("Do you want to continue ?")) {
                    break;
                }
            }
        }
    }
    public void adminScreenView () {
        try {
            while (true) {
                header("Welcome to Z-Kart Admin");
                if(!ZkartRepository.isAdminLogin){
                    System.out.println("1 -> Admin Login");
                }
                if(ZkartRepository.isAdminLogin) {
                    System.out.println("2 -> Product Operations");
                    System.out.println("3 -> Change Password");
                }
                System.out.println("4 -> Back");
                System.out.println("5 -> Exit");

                int opt = getInt("Enter Option :");
                if(opt == 1 && !ZkartRepository.isAdminLogin) {
                    AdminView.getInstance().adminLogin();
                }else if(opt == 2 && ZkartRepository.isAdminLogin) {
                    ProductView.getInstance().adminDisplay();
                }else if(opt == 3 ){
                    AdminView.getInstance().changePassword();
                }else if(opt == 4 ){
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
        }catch (Exception e) {
            System.out.println(e.getMessage());
            display();
        }
    }
}
