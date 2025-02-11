package com.zkart.screens.updateProductDetails;

import com.zkart.repository.ZkartRepository;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;

import java.util.List;

public class UpdateProductDetailsView extends BaseScreen {

    @Override
    public void display() {
        try {
            header("Admin Update Product");
            int productId = getInt("Enter product Id :");
            System.out.println("Product Details :");
            ViewProducts.printProduct(ZkartRepository.getProductById(productId));
            String category = getString("Enter Category :");
            System.out.println("use - insted of spaces ");
            String name = getString("Enter product name :");
            String description = getString("Enter Product Description :");
            String model = getString("Enter Product Model :");
            String brand = getString("Enter Product Brand :");
            int price = getInt("Enter Product Price :");
            int stock = getInt("Enter Product Count :");
            System.out.println("Confirm Adding product");
            if(ZkartRepository.updateProduct(productId, category, name, description, model, brand, price, stock))
                alert("Product Updated Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            display();
        }
    }
}
