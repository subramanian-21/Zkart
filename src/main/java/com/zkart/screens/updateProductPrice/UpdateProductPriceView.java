package com.zkart.screens.updateProductPrice;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;

public class UpdateProductPriceView extends BaseScreen {
        public void display() {
            try {
                header("Admin Product Price Update");

                int productId = getInt("Enter product Id :");
                System.out.println("Current price of product :"+ZkartRepository.getProductById(productId).getPrice());
                int price = getInt("Enter Price to update :");
                if(ZkartRepository.updateProductPrice(productId,  price));
                alert("Product price Updated Successfully");
            }catch (Exception e){
                System.out.println(e.getMessage());
                display();
            }
        }
}
