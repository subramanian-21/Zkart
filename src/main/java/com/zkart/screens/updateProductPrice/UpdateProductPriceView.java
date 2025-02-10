package com.zkart.screens.updateProductPrice;

import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;

public class UpdateProductPriceView extends BaseScreen {
        public void display() {
            try {
                header("Admin Product Price Update");
                int productId = getInt("Enter product Id :");
                int price = getInt("Enter Price to update :");
                if(ZkartRepository.updateProductPrice(productId,  price));
                alert("Product price Updated Successfully");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
}
