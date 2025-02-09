package com.zkart.screens.viewProducts;

import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;
import jdk.swing.interop.LightweightFrameWrapper;

import java.util.List;


public class ViewProducts extends BaseScreen {

    @Override
    public void display() {
        try {
            header("View Products");

            while (true) {
                System.out.println("1 -> Show All Products.");
                System.out.println("2 -> Search By Category.");
                System.out.println("3 -> Search By Name.");
                System.out.println("4 -> Back");

                int opt = getInt("Select Option :");
                int limit = 3;
                int offset = 0;
                int total = ZkartRepository.getProductCount();

                if(opt == 1) {
                    while (true){
                        List<ProductProto.Product> products = ZkartRepository.getAllProducts(limit, offset);
                        System.out.println(products.size());
                        if(!products.isEmpty()){
                            for(ProductProto.Product product : products) {
                                printProduct(product);
                            }
                        }else {
                            System.out.println("No products Available.");
                        }
                        if(offset+limit < total) {
                            System.out.println("1 -> Next Page");
                        }
                        if(offset > 0) {
                            System.out.println("2 -> Prev Page");
                        }
                        System.out.println("3 -> Exit");
                        int option = getInt("Enter Option :");
                        if(option == 1 && offset+limit < total) {
                            offset+=limit;
                        }else if(option == 2 && offset - limit >= 0) {
                            offset -= limit;
                        }else {
                            break;
                        }
                    }
                }else if(opt == 2) {
                    String category = getString("Enter Category to Search :");
                    List<ProductProto.Product>products = ZkartRepository.searchProductOnCategory(category);
                    if(products.size() == 0) {
                        System.out.println();
                        System.out.println("No Products Available in this category.");
                        System.out.println();
                    }else {
                        for (ProductProto.Product product : products) {
                            printProduct(product);
                        }
                    }
                }else if(opt == 3) {
                    String name = getString("Enter Product Name to Search :");
                    List<ProductProto.Product>products = ZkartRepository.searchProductOnName(name);
                    if(products.size() == 0) {
                        System.out.println();
                        System.out.println("No Products Available by this name.");
                        System.out.println();
                    }else {
                        for (ProductProto.Product product : products) {
                            printProduct(product);
                        }
                    }
                }else if(opt ==  4){
                    break;
                }else {
                    System.out.println("Invalid Option.");
                    if(!getBoolean("Do you want to continue ?")) {
                        break;
                    }
                }
            }

        }catch (Exception e) {
            System.out.println("Exception occured in view products.");
            e.printStackTrace();
        }
    }

    public static void printProduct(ProductProto.Product product) {
        System.out.println("______________________________________________________");
        System.out.println("Product Id :"+product.getId());
        System.out.print("Product Name :"+product.getName()+"     ");
        System.out.println("Category :"+product.getCategory());

        System.out.println("Description :"+product.getDescription());
        System.out.println("Price :"+product.getPrice());
        if(product.getStock() == 0) {
            System.out.println("Availablity : Out Of Stock");
        }
        System.out.println("_______________________________________________________");

    }
}
