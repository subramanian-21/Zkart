package com.zkart.screens.product;

import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.BaseScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductView extends BaseScreen {
    public void userDisplay() {
        try {
            header("View Products");
            while (true) {
                System.out.println("1 -> Show All Products.");
                System.out.println("2 -> Search By Category.");
                System.out.println("3 -> Search By Name.");
                System.out.println("4 -> Back");
                int dealProductId = ProductViewModel.getDealProductId();
                int opt = getInt("Select Option :");

                if(opt == 1) {
                    viewAllProducts(dealProductId);
                }else if(opt == 2) {
                    viewCategoryProducts(dealProductId);
                }else if(opt == 3) {
                   viewProductByName(dealProductId);
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
    public void adminDisplay() {
        while (true) {
            header("Admin Product Operations");
            System.out.println("1 -> Add Product");
            System.out.println("2 -> update product details");
            System.out.println("3 -> update product stock");
            System.out.println("4 -> Update Product Price");
            System.out.println("5 -> Delete Product from view");
            System.out.println("6 -> View All Products");
            System.out.println("7 -> Critical stock Products");
            System.out.println("8 -> Back");
            int dealProductId = ProductViewModel.getDealProductId();
            int opt = getInt("Choose Option :");
            if(opt == 1 && ZkartRepository.isAdminLogin) {
                addProduct();
            }else if(opt == 2 && ZkartRepository.isAdminLogin){
                updateProductDetails();
            }else if(opt == 3 && ZkartRepository.isAdminLogin){
                updateProductStock();
            }else if(opt == 4 && ZkartRepository.isAdminLogin){
                updateProductPrice();
            }else if(opt == 5 && ZkartRepository.isAdminLogin) {
                deleteProduct();
            }
            else if(opt == 6 && ZkartRepository.isAdminLogin){
                viewAllProductsAdmin();
            }else if(opt == 7 && ZkartRepository.isAdminLogin){
                viewCriticalProducts();
            }else if(opt == 8){
                break;
            }else {
                alert("Invalid Option");
                if(!getBoolean("Do you want to continue ?")) {
                    break;
                }
            }
        }
    }

    private ProductViewModel viewModel;
    private static ProductView instance;
    private ProductView(){
        viewModel = new ProductViewModel();
    }
    public static ProductView getInstance(){
        if(instance == null) {
            instance = new ProductView();
        }
        return instance;
    }
    // view all products
    public void viewAllProducts(int dealProductId){
        List<ProductProto.Product> products = ZkartRepository.getAllProducts();
        if(!products.isEmpty()){
            for(ProductProto.Product product : products) {
                printProduct(product, dealProductId);
            }
        }else {
            System.out.println("No products Available.");
        }
    }
    public void viewAllProductsAdmin(){
        List<ProductProto.Product> products = ZkartRepository.getAllProducts();
        if(!products.isEmpty()){
            for(ProductProto.Product product : products) {
                printProductAdmin(product);
            }
        }else {
            System.out.println("No products Available.");
        }
    }
    //view category products
    public void viewCategoryProducts(int dealProductId) {
        System.out.println("Available Categories :");
        Set<String> set = getCategoryList();
        for(var categoryString : set) {
            System.out.println("** "+categoryString);
        }
        String category = getString("Enter Category to Search :");
        if(!set.contains(category.toLowerCase())) {
            alert("Invalid Category");
        }
        var products = ZkartRepository.searchProductOnCategory(category);
        if(products.isEmpty()) {
            System.out.println();
            System.out.println("No Products Available in this category.");
            System.out.println();
        }else {
            for (ProductProto.Product product : products) {
                printProduct(product, dealProductId);
            }
        }
    }
    // view products by names
    public void viewProductByName(int dealProductId) {
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
    }
    // admin operatrions
    // add product
    public void addProduct() {
        try {
            header("Admin Add Product");
            Set<String> set = getCategoryList();

            String category = "";
            while (true)  {
                System.out.println("Existing Categories :");
                for(String str : set) {
                    System.out.println("** "+str);
                }
                if(set.isEmpty()) {
                    alert("No categories Available");
                }
                category = getString("Enter Category :");
                if(!set.contains(category)){
                    if(!getBoolean("Do you want to create a new category :")){
                        return;
                    }else break;
                }else break;
            }
            System.out.println("use - insted of spaces ");
            String name = getString("Enter product name :");
            String description = getString("Enter Product Description :");
            String model = getString("Enter Product Model :");
            String brand = getString("Enter Product Brand :");
            int price = 0;
            while (true) {
                price = getInt("Enter Product Price :");;
                if(price < 0) {
                    alert("Invalid Price");
                    if(!getBoolean("Do you want to try again ?")) {
                        return;
                    }
                }else {
                    break;
                }
            }

            int stock = 0;

            while (true) {
                stock = getInt("Enter Stock count :");;
                if(stock < 0) {
                    alert("Invalid stock");
                    if(!getBoolean("Do you want to try again ?")) {
                        return;
                    }
                }else {
                    break;
                }
            }
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
    // update product details
    public void updateProductDetails(){
        try {
            header("Admin Update Product");
            int productId = getInt("Enter product Id :");
            System.out.println("Product Details :");
            printProduct(ZkartRepository.getProductById(productId));
            Set<String> set = getCategoryList();
            String category = "";
            while (true)  {
                System.out.println("Existing Categories :");
                for(String str : set) {
                    System.out.println("** "+str);
                }
                if(set.isEmpty()) {
                    alert("No categories Available");
                }
                category = getString("Enter Category :");
                if(!set.contains(category)){
                    if(!getBoolean("Do you want to create a new category :")){
                        return;
                    }else break;
                }else break;
            }
            System.out.println("use - insted of spaces ");
            String name = getString("Enter product name :");
            String description = getString("Enter Product Description :");
            String model = getString("Enter Product Model :");
            String brand = getString("Enter Product Brand :");
            int price = 0;
            while (true) {
                price = getInt("Enter Product Price :");;
                if(price < 0) {
                    alert("Invalid Price");
                    if(!getBoolean("Do you want to try again ?")) {
                        return;
                    }
                }else {
                    break;
                }
            }

            int stock = 0;

            while (true) {
                stock = getInt("Enter Stock count :");;
                if(stock < 0) {
                    alert("Invalid stock");
                    if(!getBoolean("Do you want to try again ?")) {
                        return;
                    }
                }else {
                    break;
                }
            }

            if(getBoolean("Confirm Adding product") && ZkartRepository.updateProduct(productId, category, name, description, model, brand, price, stock))
                alert("Product Updated Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            display();
        }
    }
    //update product price
    public void updateProductPrice () {
        try {
            header("Admin Product Price Update");

            int productId = getInt("Enter product Id :");
            System.out.println("Current price of product :"+ZkartRepository.getProductById(productId).getPrice());
            int price = getInt("Enter Price to update :");
            if(price < 0) {
                alert("Invalid Price amount");
                return;
            }
            if(getBoolean("Do you confirm ?") && ZkartRepository.updateProductPrice(productId,  price));
            alert("Product price Updated Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            display();
        }
    }
    // update product stock
    public void updateProductStock() {
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
                if(getBoolean("Confirm Price and stock update ?") && ZkartRepository.updateProductStock(productId, stock) && ZkartRepository.updateProductPrice(productId, price)){
                    alert("Product Stock and price Updated Successfully");
                    return;
                }
            }
            if(getBoolean("Confirm stock update ?") && ZkartRepository.updateProductStock(productId,  stock))
                alert("Product Updated Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            display();
        }
    }
    // delete product
    public void deleteProduct() {
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
    // view critical products
    public void viewCriticalProducts(){
        header("Critical stock Products");
        List<ProductProto.Product> productList = ZkartRepository.getCriticalStockProducts();
        for(ProductProto.Product product : productList) {
            printProduct(product);
        }
        if(productList.isEmpty()) {
            alert("No Critical products found");
        }
    }

    public static void printProduct(ProductProto.Product product) {
        System.out.println("______________________________________________________");
        System.out.println("Product Id :"+product.getId());
        System.out.print("Product Name :"+product.getName()+"     ");
        System.out.println("Category :"+product.getCategory());
        System.out.println("Description :"+product.getDescription());
        if(product.getStock() == 0) {
            System.out.println("Availablity : Out Of Stock");
        }else if(product.getStock() < 0) {
            System.out.println("Availablity : Not available for user to view");
        }else {
            System.out.println("Price :"+product.getPrice()+"   stock :"+ product.getStock());
        }
        System.out.println("_______________________________________________________");
    }
    public static void printProductAdmin(ProductProto.Product product) {
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
        System.out.println("Added By :"+product.getAddedBy() +"   "+"Added at :"+product.getAddedAt());
        System.out.println("_______________________________________________________");
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
    public static void printProduct(ProductProto.Product product, int orderedCount, boolean isInDeal, int dealPercent) {
        System.out.println("______________________________________________________");
        System.out.println("Product Id :"+product.getId());
        System.out.print("Product Name :"+product.getName()+"     ");
        System.out.println("Category :"+product.getCategory());
        System.out.println("Description :"+product.getDescription());
        System.out.println("Price Per piece:"+product.getPrice()+"   count :"+ orderedCount);
        if(isInDeal) {
            System.out.println("Was in Deal :"+isInDeal);
            System.out.println("Deal percent :"+ dealPercent+"    "+ "Discounted Price Per Piece: "+(product.getPrice() - ((product.getPrice() * dealPercent)/100) ));
        }
        System.out.println("________________________________________________________");

    }
    public static Set<String> getCategoryList() {
        Set<String > set = new HashSet<>();
        List<ProductProto.Product>products = ZkartRepository.getAllProducts();

        for(var product : products) {
            set.add(product.getCategory().toLowerCase());
        }
        return set;
    }
}
