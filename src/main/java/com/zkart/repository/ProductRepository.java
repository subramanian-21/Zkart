package com.zkart.repository;

import com.zkart.model.ProductProto;
import com.zkart.utils.DateHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepository {
    public static ProductProto.Product getProductById(int id) throws Exception{
        if(id < 0 || id >= ZkartRepository.products.getProductsCount()) {
            throw new Exception("Invalid Product Id");
        }
        return ZkartRepository.products.getProducts(id);
    }
    public static List<ProductProto.Product> getAllProducts(){
        return  ZkartRepository.products.getProductsList();
    }

    public static List<ProductProto.Product> searchProductOnCategory(String category) {
        return ZkartRepository.products
                .getProductsList()
                .stream()
                .filter(pro -> pro.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    public static List<ProductProto.Product> searchProductOnName(String name) {
        return ZkartRepository.products
                .getProductsList()
                .stream()
                .filter(pro -> pro.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    public static boolean addProduct(String category, String name, String description, String model, String brand, int price, int stock) throws Exception{

        if(ZkartRepository.isAdminLogin) {

            ProductProto.Product product = ProductProto
                    .Product.newBuilder()
                    .setId(ZkartRepository.products.getProductsCount())
                    .setCategory(category)
                    .setName(name)
                    .setDescription(description)
                    .setModel(model)
                    .setBrand(brand)
                    .setPrice(price)
                    .setStock(stock)
                    .setAddedBy(ZkartRepository.admin.getAdminUser().getEmail())
                    .setAddedAt(DateHandler.getTimeStamp())
                    .build();

            ZkartRepository.products = ZkartRepository.products.toBuilder().addProducts(product).build();
            FileOutputStream fos = null;
            FileInputStream fis = null;
            try {
                String filePath = ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                ZkartRepository.products.writeTo(fos);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return true;
        }
        return false;
    }
    public static boolean updateProductStock(int productId, int stock) throws Exception {
        if(stock < 0) {
            throw new Exception("Invalid Stock Count");
        }
        if(ZkartRepository.isAdminLogin) {

            ProductProto.Product product = ZkartRepository.products.getProducts(productId).toBuilder().setStock(stock).setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(ZkartRepository.admin.getAdminUser().getEmail()).build();
            ZkartRepository.products = ZkartRepository.products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            try {
                String filePath = ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                ZkartRepository.products.writeTo(fos);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return true;
        }
        return false;
    }
    public static boolean updateProductPrice(int productId, int price) throws Exception {
        if(price < 0) {
            throw new Exception("Invalid Price Amount");
        }
        if(ZkartRepository.isAdminLogin) {

            ProductProto.Product product = ZkartRepository.products.getProducts(productId).toBuilder().setPrice(price).setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(ZkartRepository.admin.getAdminUser().getEmail()).build();
            ZkartRepository.products = ZkartRepository.products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            try {
                String filePath = ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                ZkartRepository.products.writeTo(fos);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return true;
        }
        return false;
    }
    public static boolean deleteProduct(int productId) throws Exception {
        if(ZkartRepository.isAdminLogin) {

            ProductProto.Product product = ZkartRepository.products.getProducts(productId).toBuilder().setStock(-1).setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(ZkartRepository.admin.getAdminUser().getEmail()).build();
            ZkartRepository.products = ZkartRepository.products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            try {
                String filePath = ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                ZkartRepository.products.writeTo(fos);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return true;
        }
        return false;
    }
    public static boolean updateProduct(int productId, String category, String name, String description, String model, String brand, int price, int stock) throws Exception {
        if(price < 0 || stock < 0) throw new Exception("Invalid Price or count");
        if(ZkartRepository.isAdminLogin) {

            ProductProto.Product product = ProductProto
                    .Product.newBuilder()
                    .setId(productId)
                    .setCategory(category)
                    .setName(name)
                    .setDescription(description)
                    .setModel(model)
                    .setBrand(brand)
                    .setPrice(price)
                    .setStock(stock)
                    .setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(ZkartRepository.admin.getAdminUser().getEmail())
                    .build();

            ZkartRepository.products = ZkartRepository.products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            FileInputStream fis = null;
            try {
                String filePath = ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                ZkartRepository.products.writeTo(fos);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return true;
        }
        return false;
    }
    public static List<ProductProto.Product> getCriticalStockProducts() {
        List<ProductProto.Product> productList =ZkartRepository.products.getProductsList();

        List<ProductProto.Product> tempList = new ArrayList<>();
        for(int i = 0;i<productList.size();i++) {
            if(productList.get(i).getStock() <= ZkartRepository.PRODUCT_CRITICAL_COUNT) {
                tempList.add(productList.get(i));
            }
        }
        return tempList;
    }
    public static List<Integer> updateProductStockAndGetProductIdList(List<ProductProto.Product> productsList, List<Integer> stocks) {
        List<Integer> productIds = new ArrayList<>();
        for(int i = 0;i<productsList.size();i++) {
            ProductProto.Product product = productsList.get(i);
            productIds.add(product.getId());
            int newStock = product.getStock() - stocks.get(i);
            product = product.toBuilder().setStock(newStock).build();
            ZkartRepository.products = ZkartRepository.products.toBuilder().setProducts(product.getId(), product).build();
        }
        return productIds;
    }

}
