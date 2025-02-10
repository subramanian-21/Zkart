package com.zkart.screens.adminWelcome;

import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.addProducts.AddProductsView;
import com.zkart.screens.adminLogin.AdminLoginView;
import com.zkart.screens.changePassword.ChangePasswordView;
import com.zkart.screens.updateProductDetails.UpdateProductDetailsView;
import com.zkart.screens.updateProductPrice.UpdateProductPriceView;
import com.zkart.screens.updateProductStock.UpdateProductStockView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;

import java.util.List;

public class AdminWelcomeView extends BaseScreen {
    @Override
    public void display() {
        try {
            while (true) {
                header("Welcome to Z-Kart Admin");
                if(!ZkartRepository.isAdminLogin){
                    System.out.println("1 -> Admin Login");
                }
                if(ZkartRepository.isAdminLogin) {
                    System.out.println("2 -> Add Product");
                    System.out.println("3 -> update product details");
                    System.out.println("4 -> update product stock");
                    System.out.println("5 -> Update Product Price");
                    System.out.println("6 -> View All Products");
                    System.out.println("7 -> Critical stock Products");
                    System.out.println("8 -> Admin password Update");
                    System.out.println("9 -> Logout");
                }
                System.out.println("10 -> Back");
                System.out.println("11 -> Exit");

                int opt = getInt("Enter Option :");
                if(opt == 1 && !ZkartRepository.isAdminLogin) {
                    navigate(new AdminLoginView());
                }else if(opt == 2 && ZkartRepository.isAdminLogin) {
                    navigate(new AddProductsView());
                }else if(opt == 3 && ZkartRepository.isAdminLogin){
                    // update product details
                    navigate(new UpdateProductDetailsView());
                }else if(opt == 4 && ZkartRepository.isAdminLogin){
                    // update product stock
                    navigate(new UpdateProductStockView());
                }else if(opt == 5 && ZkartRepository.isAdminLogin){
                    // View All Products
                    navigate(new UpdateProductPriceView());
                }else if(opt == 6 && ZkartRepository.isAdminLogin){
                    // View All Products
                    navigate(new ViewProducts());
                }else if(opt == 7 && ZkartRepository.isAdminLogin){
                    // View less threshold Products
                    header("Critical stock Products");
                    List<ProductProto.Product> productList = ZkartRepository.getCriticalStockProducts();
                    for(ProductProto.Product product : productList) {
                        ViewProducts.printProduct(product);
                    }
                    if(productList.isEmpty()) {
                        alert("No Critical products found");
                    }
                }else if(opt == 8 && ZkartRepository.isAdminLogin) {
                    ZkartRepository.updateAdminPassword(new ChangePasswordView().getPasswords());
                }else if(opt == 9  && ZkartRepository.isAdminLogin){
                    ZkartRepository.logout();
                }else if(opt == 10 ){
                    ZkartRepository.logout();
                    break;
                }else if(opt == 11){
                    return;
                }else {
                    System.out.println("Invalid Option");
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
