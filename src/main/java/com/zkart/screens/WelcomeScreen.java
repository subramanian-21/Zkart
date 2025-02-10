package com.zkart.screens;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.addProducts.AddProductsView;
import com.zkart.screens.adminLogin.AdminLoginView;
import com.zkart.screens.adminWelcome.AdminWelcomeView;
import com.zkart.screens.changePassword.ChangePasswordView;
import com.zkart.screens.changePasswordForUser.ChangePasswordForUserView;
import com.zkart.screens.myCoupons.MyCouponsView;
import com.zkart.screens.myOrders.MyOrdersView;
import com.zkart.screens.orderProduct.OrderProductView;
import com.zkart.screens.signIn.SignInView;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.screens.userWelcome.UserWelcomeView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.exceptions.TerminatePageInterupt;

public class WelcomeScreen extends BaseScreen {
    @Override
    public void display() {
            while (true) {
                if(getBoolean("Are you a User ? ")){
                    navigate(new UserWelcomeView());
                }else {
                    navigate(new AdminWelcomeView());
                }
            }
    }
}
