package com.zkart.screens.product;

import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;

import java.util.List;

public class ProductViewModel {
    public static int getDealProductId(){
        List<ProductProto.Product> products = ZkartRepository.getAllProducts();
        int max = 0;
        int id = -1;

        for(ProductProto.Product product : products) {
            if(max < product.getStock()) {
                id = product.getId();
                max = product.getStock();
            }
        }
        return id;
    }
}
