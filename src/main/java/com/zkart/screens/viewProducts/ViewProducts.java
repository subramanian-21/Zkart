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
                int dealProductId = getDealProductId();
                int opt = getInt("Select Option :");

                if(opt == 1) {
                    while (true){
                        List<ProductProto.Product> products = ZkartRepository.getAllProducts();
                        if(!products.isEmpty()){
                            for(ProductProto.Product product : products) {
                                printProduct(product, dealProductId);
                            }
                        }else {
                            System.out.println("No products Available.");
                        }
                        System.out.println("3 -> Exit");
                        int option = getInt("Enter Option :");
                        if(option == 3) {
                            break;
                        }else {
                            alert("Invalid Option");
                            if(!getBoolean("Do you want to continue ?")){
                                break;
                            }
                        }
                    }
                }else if(opt == 2) {
                    String category = getString("Enter Category to Search :");
                    List<ProductProto.Product>products = ZkartRepository.searchProductOnCategory(category);
                    if(products.isEmpty()) {
                        System.out.println();
                        System.out.println("No Products Available in this category.");
                        System.out.println();
                    }else {
                        for (ProductProto.Product product : products) {
                            printProduct(product, dealProductId);
                        }
                    }
                }else if(opt == 3) {
                    String name = getString("Enter Product Name to Search :");
                    List<ProductProto.Product>products = ZkartRepository.searchProductOnName(name);
                    if(products.isEmpty()) {
                        System.out.println();
                        System.out.println("No Products Available by this name.");
                        System.out.println();
                    }else {
                        for (ProductProto.Product product : products) {
                            printProduct(product, dealProductId);
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

    public static void printProduct(ProductProto.Product product, int dealProductId) {
        if(product.getStock() == -1) {
            return;
        }
        System.out.println("______________________________________________________");
        System.out.println("Product Id :"+product.getId());
        System.out.print("Product Name :"+product.getName()+"     ");
        System.out.println("Category :"+product.getCategory());
        System.out.println("Description :"+product.getDescription());
        if(dealProductId == product.getId()) {
            System.out.println("***   Deal of the Minute   ***");
            System.out.println("10 Percent Discount applied...");
          System.out.println("Actual Price :" +product.getPrice()+"    discounted price :"+ (product.getPrice() - (product.getPrice()/10)));
            System.out.println("Stock count :"+product.getStock());
        }else {
            System.out.println("Price :"+product.getPrice()+"   stock :"+ product.getStock());
        }
        if(product.getStock() == 0) {
            System.out.println("Availablity : Out Of Stock");
        }
        System.out.println("________________________________________________________");

    }
    public static void printProduct(ProductProto.Product product) {
        System.out.println("______________________________________________________");
        System.out.println("Product Id :"+product.getId());
        System.out.print("Product Name :"+product.getName()+"     ");
        System.out.println("Category :"+product.getCategory());
        System.out.println("Description :"+product.getDescription());
        if(product.getStock() <= 0) {
            System.out.println("Availablity : Out Of Stock");
        }else {
            System.out.println("Price :"+product.getPrice()+"   stock :"+ product.getStock());
        }
        System.out.println("_______________________________________________________");
    }
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
