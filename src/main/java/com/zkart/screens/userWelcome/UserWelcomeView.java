package com.zkart.screens.userWelcome;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.changePasswordForUser.ChangePasswordForUserView;
import com.zkart.screens.myCoupons.MyCouponsView;
import com.zkart.screens.myOrders.MyOrdersView;
import com.zkart.screens.orderProduct.OrderProductView;
import com.zkart.screens.signIn.SignInView;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

public class UserWelcomeView extends BaseScreen {
    private UserWelcomeViewModel viewModel;
    public UserWelcomeView (){
        viewModel = new UserWelcomeViewModel();
    }
    @Override
    public void display() {
        try {
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
                            navigate(new LoginView());

                        } else if (opt == 2 && !ZkartRepository.isUserLogin) {
                            navigate(new SignInView());
                            navigate(new LoginView());

                        } else if (opt == 3) {
                            navigate(new ViewProducts());
                        } else if (opt == 4) {
                            navigate(new OrderProductView());
                        } else if (opt == 5 && ZkartRepository.isUserLogin) {
                            navigate(new MyOrdersView());
                        } else if (opt == 6 && ZkartRepository.isUserLogin) {
                            navigate(new MyCouponsView());
                        } else if (opt == 7 && ZkartRepository.isUserLogin) {
                            ZkartRepository.logout();
                        } else if (opt == 8 && ZkartRepository.isUserLogin) {
                            String newPassword = new ChangePasswordForUserView().getPasswords();
                            if(viewModel.validateAndChangePassword(newPassword)){
                                alert("Successfully changed password");
                            }else {
                                alert("Password validation failed");
                                System.out.println("* password must not be same as the last three passwords.");
                            }

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
        }catch (InvalidPasswordSyntaxException e) {
            System.out.println(PasswordHandler.getValidPasswordSyntax());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
