package com.zkart.screens.updateProductStock;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;

public class UpdateProductStockView extends BaseScreen {
    @Override
    public void display() {
        try {
            header("Admin Product stock Update");
            int productId = getInt("Enter product Id :");
            System.out.println("Current stock :"+ZkartRepository.getProductById(productId).getStock());

            int stock = getInt("Enter Stock to update :");
            if(getBoolean("Do you want to update price also ? ")){
                int price = getInt("Enter product price :");
                if(price < 0){
                    throw new Exception("Invalid Price Amount");
                }
                if(ZkartRepository.updateProductStock(productId, stock) && ZkartRepository.updateProductPrice(productId, price)){
                    alert("Product Stock and price Updated Successfully");
                    return;
                }
            }
            if(ZkartRepository.updateProductStock(productId,  stock))
                alert("Product Updated Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            display();
        }
    }
}
