package com.zkart.screens;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;

public class DeleteProduct extends BaseScreen {

    @Override
    public void display() {
        try {
            header("Admin Product Delete from View");

            int productId = getInt("Enter product Id :");
            ZkartRepository.getProductById(productId);
            if(getBoolean("Are you sure u want to delete ?")){
                ZkartRepository.deleteProduct(productId);
            }
            alert("Product Deleted from view Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            display();
        }
    }
}
