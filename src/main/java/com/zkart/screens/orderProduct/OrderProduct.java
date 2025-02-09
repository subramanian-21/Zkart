package com.zkart.screens.orderProduct;

import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;

import java.util.ArrayList;
import java.util.List;

public class OrderProduct extends BaseScreen {

    @Override
    public void display() {
        try {
            header("Order Product");
            if(!ZkartRepository.isUserLogin) {
                navigate(new LoginView());
            }
            List<ProductProto.Product> products = new ArrayList<>();
            List<Integer> stocks = new ArrayList<>();
            while(true) {
                System.out.println("1 -> Book Product By Id :");
                System.out.println("2 -> Search for product :");
                System.out.println("3 -> Back");
                int opt = getInt("Select Option :");
                if(opt == 1) {
                    int id = getInt("Enter Product Id :");
                    ProductProto.Product product = ZkartRepository.getProductById(id);
                    if(product.getStock() != 0){
                        int stock = getInt("Enter Count :");
                        if(stock > 0 && stock < product.getStock()) {
                            products.add(product);
                            stocks.add(stock);
                        }else {
                            alert("Stock Unavailable");
                        }
                    }
                    if(!getBoolean("Do you want to add more ?")){
                        if(ZkartRepository.createOrder(ZkartRepository.loggedInUser.getId(), products, stocks)){
                            System.out.println("Order created successfully...");
                        }

                    }
                } else if (opt == 2) {
                    navigate(new ViewProducts());
                }else if(opt == 3) {
                    break;
                }else {
                    alert("Invalid Option.");
                    if(!getBoolean("Do you want to continue ?")) {
                        break;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
