package com.zkart.screens.addProducts;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;

public class AddProductsView extends BaseScreen {
    @Override
    public void display() {
        try {
            header("Admin Add Product");

            String category = getString("Enter Category :");
            System.out.println("use - insted of spaces ");
            String name = getString("Enter product name :");
            String description = getString("Enter Product Description :");
            String model = getString("Enter Product Model :");
            String brand = getString("Enter Product Brand :");
            int price = getInt("Enter Product Price :");
            int stock = getInt("Enter Product Count :");
            if(getBoolean("confirm add :")) {
                if(ZkartRepository.addProduct(category, name, description, model, brand, price, stock))
                    alert("Product Added Successfully");
            }else {
                alert("product addition unsuccessful");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Add Products page Terminated");
        }
    }
}
