package com.zkart.screens.myOrders;

import com.zkart.model.OrderProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;

import java.util.List;

public class MyOrdersView extends BaseScreen {
    @Override
    public void display() {
        if(!ZkartRepository.isUserLogin) {
            navigate(new LoginView());
        }
        if(ZkartRepository.isUserLogin) {
            header("My Orders");
            List<OrderProto.Order> orders = ZkartRepository.getUserOrders(ZkartRepository.loggedInUser.getId());
            if(orders.isEmpty()) {
                alert("No Orders Found");
                return;
            }
            for (OrderProto.Order order : orders) {
                printOrders(order);
            }
        }
    }

    public void printOrders(OrderProto.Order order) {
        System.out.println("=======================================================");
        System.out.println("Invoice Number :"+order.getId());
        System.out.println("Total Price :"+order.getTotalPrice());
        System.out.println("Products :");
        for(int temp : order.getProductIdsList()) {
            ViewProducts.printProduct(ZkartRepository.getProductById(temp));
        }
        System.out.println("=======================================================");
        System.out.println();
    }
}
