package com.zkart.repository;

import com.zkart.model.*;
import com.zkart.screens.adminLogin.AdminLoginView;
import com.zkart.screens.changePassword.ChangePasswordView;
import com.zkart.utils.DateHandler;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.UniqueId;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCouponException;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ZkartRepository {
    public static final String DEFAULT_ADMIN_PASSWORD = "12345";
    public static final String DB_FILE_ROOT_PATH = "/Users/subramani-22949/IdeaProjects/z-kart/src/main/java/com/zkart/dbFiles/";
    public static final int COUPON_USAGE_COUNT = 3;
    public static final  int COUPON_EXPIRATION_DAYS = 1;
    public static final  int COUPON_DISCOUNT_PERCENT_START = 20;
    public static final  int COUPON_DISCOUNT_PERCENT_END = 30;
    public static final int PRODUCT_CRITICAL_COUNT = 10;
    public static final int DEAL_DISCOUNT_PERCENT = 10;

    private static ProductProto.Products products;
    private static OrderProto.Orders orders;
    private static AdminProto.Admin admin;
    private static UserProto.Users users;
    private static CouponProto.Coupons coupons;

    public static UserProto.User loggedInUser;
    public static boolean isAdminLogin;
    public static boolean isUserLogin;

    static {
        FileInputStream adminFis = null;
        FileInputStream productFis = null;
        FileInputStream userFis = null;
        FileInputStream couponFis = null;
        FileInputStream orderFis = null;
        try {
            adminFis = new FileInputStream(new File(DB_FILE_ROOT_PATH + "admin_db.txt"));
            productFis = new FileInputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
            userFis = new FileInputStream(new File(DB_FILE_ROOT_PATH + "user_db.txt"));
            couponFis = new FileInputStream(new File(DB_FILE_ROOT_PATH + "coupon_db.txt"));
            orderFis = new FileInputStream(new File(DB_FILE_ROOT_PATH + "order_db.txt"));

            FileOutputStream adminFos = null;

            // ADMIN INITIALIZATION
            try {
                admin = AdminProto.Admin.parseFrom(adminFis);
            }catch (Exception e) {
                admin = AdminProto
                        .Admin
                        .newBuilder()
                        .setId("aabb")
                        .setFullname("admin")
                        .setEmail("admin@zoho.com")
                        .setPassword(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD))
                        .build();

                adminFos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "admin_db.txt"));
                admin.writeTo(adminFos);
            }finally {
                if (adminFos != null) {
                    try {
                        adminFos.close();
                    }catch (IOException e) {
                        System.out.println("Exception :Admin Fos doesn't close");
                    }
                }
            }
            // PRODUCT INITIALIZATION
            try {
                products =  ProductProto.Products.parseFrom(productFis);
            }catch (Exception e) {
                FileOutputStream productFos = null;
                BufferedReader bufferedReader = null;

                List<ProductProto.Product> productList = new ArrayList<>();
                try {

                    productFos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
                    bufferedReader = new BufferedReader(new FileReader(new File(DB_FILE_ROOT_PATH + "default_products.txt")));
                    String product = null;
                    int maxStock = 0;
                    ProductProto.Product maxStockProduct = null;
                    while (bufferedReader.ready()) {
                        product = bufferedReader.readLine();
                        String[] splitProduct = product.split("\\|");
                        ProductProto.Product productProto = ProductProto
                                .Product
                                .newBuilder()
                                .setId(Integer.parseInt(splitProduct[0]))
                                .setCategory(splitProduct[1])
                                .setName(splitProduct[2])
                                .setDescription(splitProduct[3])
                                .setModel(splitProduct[4])
                                .setBrand(splitProduct[5])
                                .setPrice(Integer.parseInt(splitProduct[6]))
                                .setStock(Integer.parseInt(splitProduct[7]))
                                .build();
                        productList.add(productProto);
                        if(maxStock < productProto.getStock()) {
                            maxStockProduct = productProto;
                            maxStock = productProto.getStock();
                        }
                    }
                    maxStockProduct = maxStockProduct.toBuilder().setIsInDeal(true).build();
                    products = ProductProto.Products.newBuilder().addAllProducts(productList).setProducts(maxStockProduct.getId(), maxStockProduct).build();
                    products.writeTo(productFos);

                }catch (IOException e1) {
                    e1.printStackTrace();
                }finally {
                    if(bufferedReader != null) {
                        try{
                            bufferedReader.close();
                        }catch (Exception e2) {
                            throw new RuntimeException("Error Occured in Buffered Reader");
                        }
                    }
                    if(productFos != null) {
                        try {
                            productFis.close();
                        }catch (Exception e2) {
                            throw new RuntimeException("Error occured in Product FIS");
                        }
                    }
                }
            }
            // USER INITIALIZATION
            try {
                users = UserProto.Users.parseFrom(userFis);
            }catch (Exception e) {
                users = UserProto.Users.newBuilder().build();
                System.out.println("User Initializing for the first time");
            }
            // COUPON INITIALIZATION
            try {
                coupons = CouponProto.Coupons.parseFrom(couponFis);
            }catch (Exception e) {
                coupons = CouponProto.Coupons.newBuilder().build();
                System.out.println("Coupons Initializing for the first time");
            }
            // ORDER INITIALIZATION

           try {
               orders = OrderProto.Orders.parseFrom(orderFis);
           }catch (Exception e) {
               orders = OrderProto.Orders.newBuilder().build();
               System.out.println("Order Initialized for the first time");
           }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(adminFis != null) {
                try {
                    adminFis.close();
                }catch (Exception e) {
                    throw new RuntimeException("Error occured in Admin FIS");
                }
            }
            if(productFis != null) {
                try {
                    productFis.close();
                }catch (Exception e) {
                    throw new RuntimeException("Error occured in Product FIS");
                }
            }
            if(userFis != null) {
                try {
                    userFis.close();
                }catch (Exception e) {
                    throw new RuntimeException("Error occured in User FIS");
                }
            }
            if(couponFis != null) {
                try {
                    couponFis.close();
                }catch (Exception e) {
                    throw new RuntimeException("Error occured in Coupon FIS");
                }
            }
            if(orderFis != null) {
                try {
                    orderFis.close();
                } catch (Exception e) {
                    throw new RuntimeException("Error occured in order FIS");
                }
            }
        }
    }
    public static int getProductsCount() {
        return products.getProductsCount();
    }
    public static int getProductCount() {
        return products.getProductsCount();
    }
    public static ProductProto.Product getProductById(int id) {
        return products.getProducts(id);
    }

    public static boolean validateUser(String email, String password) throws InvalidCredentialsException {
        List<UserProto.User> tempList = users.getUsersList();
        for (UserProto.User user : tempList) {

                String encrypt = PasswordHandler.encryptPassword(password);
                if(user.getEmail().equals(email) && user.getPassword().equals(encrypt)) {
                    loggedInUser = user;
                    isAdminLogin = false;
                    return isUserLogin = true;
                }
        }
        loggedInUser = null;
        throw new InvalidCredentialsException();
    }
    public static boolean validateAdmin(String email, String password)  throws InitialAdminLoginException, InvalidCredentialsException {

        boolean temp = false;

        if(admin.getPassword().equals(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD)) && password.equals(DEFAULT_ADMIN_PASSWORD)) {
            System.out.println("Initial admin login");
                throw new InitialAdminLoginException();
            }
            temp = admin.getEmail().equals(email)
                    && admin.getPassword().equals(PasswordHandler.encryptPassword(password));

        if(temp) {
            isAdminLogin = true;
            isUserLogin = false;
            return true;
        }else {
            isAdminLogin = false;
            isUserLogin = false;
        }
        throw new InvalidCredentialsException();
    }
    public static void logout(){
        isAdminLogin = false;
        isUserLogin = false;
    }

    public static boolean userSignIn(String name, String email, String password) {
        UserProto.User user = UserProto
                .User
                .newBuilder()
                .setId(users.getUsersCount())
                .setFullname(name)
                .setEmail(email)
                .setPassword(PasswordHandler.encryptPassword(password))
                .addPrePasswords(PasswordHandler.encryptPassword(password))
                .build();

        users = users.toBuilder().addUsers(user).build();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH+"user_db.txt"));
            users.writeTo(fos);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                }catch (IOException e){
                    throw new RuntimeException();
                }
            }
        }
        return true;
    }
    public static boolean signInEmailValidation(String email) {
        List<UserProto.User> userList = users.getUsersList();

        for(UserProto.User user : userList) {
            if(user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public static List<ProductProto.Product> getAllProducts(int limit, int offset){
        List<ProductProto.Product> productList = products.getProductsList();

        if(offset >= productList.size()) {
            return new ArrayList<>();
        }

        System.out.println(productList.size());
//        return productList.subList(offset, Math.min(offset+limit, productList.size()));
        return productList;
    }
    public static List<ProductProto.Product> searchProductOnCategory(String category) {
        return products
                .getProductsList()
                .stream()
                .filter(pro -> pro.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    public static List<ProductProto.Product> searchProductOnName(String name) {
        return products
                .getProductsList()
                .stream()
                .filter(pro -> pro.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public static void updateAdminPassword(String password) {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "admin_db.txt"));
            admin = admin.toBuilder().setPassword(PasswordHandler.encryptPassword(password)).build();
            admin.writeTo(fos);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                fos.close();
                }catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }
    }
    public static boolean updateUserPassword(String password) {
        if(!isUserLogin) {
            return false;
        }
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "user_db.txt"));
            loggedInUser = loggedInUser.toBuilder().setPassword(PasswordHandler.encryptPassword(password)).addPrePasswords(PasswordHandler.encryptPassword(password)).build();
            users = users.toBuilder().setUsers(loggedInUser.getId(), loggedInUser).build();
            users.writeTo(fos);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                }catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }
        return true;
    }
    public static boolean addProduct(String category, String name, String description, String model, String brand, int price, int stock) throws Exception{
        new AdminLoginView().display();
       if(isAdminLogin) {
           if(admin.getPassword().equals(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD))) {
               String newPassword = new ChangePasswordView().getPasswords(DEFAULT_ADMIN_PASSWORD);
               if(newPassword == null) {
                   throw new Exception("Adding Product Terminated.");
               }
               updateAdminPassword(newPassword);
           }
           ProductProto.Product product = ProductProto
                   .Product.newBuilder()
                   .setId(products.getProductsCount())
                   .setCategory(category)
                   .setName(name)
                   .setDescription(description)
                   .setModel(model)
                   .setBrand(brand)
                   .setPrice(price)
                   .setStock(stock)
                   .build();

           products = products.toBuilder().addProducts(product).build();
           FileOutputStream fos = null;
           FileInputStream fis = null;
           try {
               String filePath = DB_FILE_ROOT_PATH + "product_db.txt";
               fos = new FileOutputStream(new File(filePath));
               products.writeTo(fos);
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
        if(stock <= 0) {
            throw new Exception("Invalid Stock Count");
        }
        new AdminLoginView().display();
        if(isAdminLogin) {
            if(admin.getPassword().equals(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD))) {
                String newPassword = new ChangePasswordView().getPasswords(DEFAULT_ADMIN_PASSWORD);
                if(newPassword == null) {
                    throw new Exception("Adding Product Terminated.");
                }
                updateAdminPassword(newPassword);
            }
            ProductProto.Product product = products.getProducts(productId).toBuilder().setStock(stock).build();
            products = products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            try {
                String filePath = DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                products.writeTo(fos);
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
        new AdminLoginView().display();
        if(isAdminLogin) {
            if(admin.getPassword().equals(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD))) {
                String newPassword = new ChangePasswordView().getPasswords(DEFAULT_ADMIN_PASSWORD);
                if(newPassword == null) {
                    throw new Exception("Adding Product Terminated.");
                }
                updateAdminPassword(newPassword);
            }
            ProductProto.Product product = products.getProducts(productId).toBuilder().setPrice(price).build();
            products = products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            try {
                String filePath = DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                products.writeTo(fos);
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
        new AdminLoginView().display();
        if(isAdminLogin) {
            if(admin.getPassword().equals(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD))) {
                String newPassword = new ChangePasswordView().getPasswords(DEFAULT_ADMIN_PASSWORD);
                if(newPassword == null) {
                    throw new Exception("Adding Product Terminated.");
                }
                updateAdminPassword(newPassword);
            }
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
                    .build();

            products = products.toBuilder().setProducts(productId, product).build();
            FileOutputStream fos = null;
            FileInputStream fis = null;
            try {
                String filePath = DB_FILE_ROOT_PATH + "product_db.txt";
                fos = new FileOutputStream(new File(filePath));
                products.writeTo(fos);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                fos.close();
            }
            return true;
        }
        return false;
    }
    public static UserProto.User getUserById(int userId) {
        return users.getUsers(userId);
    }
    public static List<ProductProto.Product> getCriticalStockProducts() {
        List<ProductProto.Product> productList =products.getProductsList();

        List<ProductProto.Product> tempList = new ArrayList<>();
        for(int i = 0;i<productList.size();i++) {
            if(productList.get(i).getStock() <= PRODUCT_CRITICAL_COUNT) {
                tempList.add(productList.get(i));
            }
        }
        return tempList;
    }
    public static boolean createOrder(int userId, List<ProductProto.Product> productsList, List<Integer> stocks, int totalPrice) {
        int id = orders.getOrdersCount();
        List<Integer> productIds = updateProductStockAndGetProductIdList(productsList, stocks);
        Thread productUpdateThread = new Thread(()->updateDealProduct());
        productUpdateThread.start();
        OrderProto.Order order = OrderProto
                .Order
                .newBuilder()
                .setUserId(userId)
                .setTotalPrice(totalPrice)
                .setFinalPrice(totalPrice)
                .addAllProductIds(productIds)
                .setIsCouponApplied(false)
                .setId(id+100)
                .build();
        FileOutputStream fosOrder = null;
        FileOutputStream fosProduct = null;
        try {
            fosOrder = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "order_db.txt"));
            fosProduct = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
            orders = orders.toBuilder().addOrders(order).build();
            orders.writeTo(fosOrder);
            products.writeTo(fosProduct);
        }
        catch (IOException e) {
            return false;
        }
        finally {
            if(fosOrder != null) {
                try {
                    fosOrder.close();
                }catch (IOException e) {
                    throw new RuntimeException("Exception in FOS Order");
                }
            }
            if(fosProduct != null) {
                try {
                    fosProduct.close();
                }catch (IOException e) {
                    throw new RuntimeException("Exception in FOSProduct");
                }
            }
        }

        return true;
    }
    public static boolean createOrder(int userId, List<ProductProto.Product> productsList, List<Integer> stocks, int totalPrice, int finalPrice, CouponProto.Coupon coupon) throws InvalidCouponException {
        int id = orders.getOrdersCount();
        List<Integer> productIds = updateProductStockAndGetProductIdList(productsList, stocks);
        Thread productUpdateThread = new Thread(()->updateDealProduct());
        productUpdateThread.start();
        OrderProto.Order order = OrderProto
                .Order
                .newBuilder()
                .setUserId(userId)
                .setTotalPrice(totalPrice)
                .setFinalPrice(totalPrice)
                .addAllProductIds(productIds)
                .setIsCouponApplied(false)
                .setCouponCode(coupon.getId())
                .setDiscountPercent(coupon.getDiscountPercent())
                .setFinalPrice(finalPrice)
                .setId(id+100)
                .build();
        FileOutputStream fosOrder = null;
        FileOutputStream fosProduct = null;
        FileOutputStream fosCoupon = null;
        try {
            fosOrder = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "order_db.txt"));
            fosProduct = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
            fosCoupon = new FileOutputStream(new File(DB_FILE_ROOT_PATH +"coupon_db.txt"));
            coupon = coupon.toBuilder().setRemainingCount(coupon.getRemainingCount()-1).build();
            coupons = coupons.toBuilder().setCoupons(getCouponIndex(coupon.getId()), coupon).build();
            orders = orders.toBuilder().addOrders(order).build();
            orders.writeTo(fosOrder);
            products.writeTo(fosProduct);
            coupons.writeTo(fosCoupon);
        }
        catch (IOException e) {
            return false;
        }
        finally {
            if(fosOrder != null) {
                try {
                    fosOrder.close();
                }catch (IOException e) {
                    throw new RuntimeException("Exception in FOS Order");
                }
            }
            if(fosProduct != null) {
                try {
                    fosProduct.close();
                }catch (IOException e) {
                    throw new RuntimeException("Exception in FOSProduct");
                }
            }
        }
        return true;
    }
    public static boolean createCoupon(int userId) {
        FileOutputStream fosCoupon = null;
        try {

            fosCoupon = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "coupon_db.txt"));
            CouponProto.Coupon coupon = CouponProto
                        .Coupon
                        .newBuilder()
                        .setId(UniqueId.getCouponId(userId))
                        .setDateIssued(DateHandler.getCurrentDate())
                        .setValidTill(DateHandler.getDateAfter(COUPON_EXPIRATION_DAYS))
                        .setUserId(userId)
                        .setDiscountPercent(UniqueId.generateRandomInt(COUPON_DISCOUNT_PERCENT_START, COUPON_DISCOUNT_PERCENT_END))
                        .setRemainingCount(COUPON_USAGE_COUNT)
                        .build();

                coupons = coupons.toBuilder().addCoupons(coupon).build();
                coupons.writeTo(fosCoupon);
                return true;
        }
        catch (Exception e) {
            return false;
        }
        finally {
            if(fosCoupon != null) {
                try {
                    fosCoupon.close();
                }catch (IOException e) {
                    throw new RuntimeException("Exception at FOS Coupon");
                }
            }
        }
    }
    public static int getCouponIndex(String couponId) throws InvalidCouponException {
        List<CouponProto.Coupon> couponList = coupons.getCouponsList();
        for(int i = 0;i<couponList.size();i++) {
            if(couponList.get(i).getId().equals(couponId)){
                return i;
            }
        }
        throw new InvalidCouponException();
    }
    public static List<CouponProto.Coupon> getUserCoupons() {
        List<CouponProto.Coupon> couponList = new ArrayList<>();

        for(CouponProto.Coupon c : coupons.getCouponsList()) {
            if(c.getUserId() == loggedInUser.getId()) {
                couponList.add(c);
            }
        }
        return couponList;
    }
    public static List<OrderProto.Order> getUserOrders(int userId) {
        List<OrderProto.Order> orderList = orders.getOrdersList();
        List<OrderProto.Order> userOrderList = new ArrayList<>();

        for (OrderProto.Order temp : orderList) {
            if(temp.getUserId() == userId) {
                System.out.println(temp.getUserId());
                userOrderList.add(temp);
            }
        }

        return userOrderList;
    }
    public static CouponProto.Coupon getCoupon(String couponId) {
        for(CouponProto.Coupon c : coupons.getCouponsList()) {
            if(c.getId().equals(couponId)) {
               return c;
            }
        }
        return null;
    }
    public static void updateDealProduct() {
        ProductProto.Product dealProduct = null;
        int maxStock = 0;
        ProductProto.Product newDealProduct = null;

        List<ProductProto.Product> productList = products.getProductsList();

        for(ProductProto.Product temp : productList) {
            if(temp.getStock() > maxStock) {
                maxStock = temp.getStock();
                newDealProduct = temp;
            }
            if(temp.getIsInDeal()) {
                dealProduct = temp;
            }
        }
        if(dealProduct == null && newDealProduct == null) return;
        if(newDealProduct != null) {
            newDealProduct = newDealProduct.toBuilder().setIsInDeal(true).build();
            return;
        }
        if(dealProduct.getId() == newDealProduct.getId() || dealProduct.getStock() == newDealProduct.getStock()) {
            return;
        }
        dealProduct = dealProduct.toBuilder().setIsInDeal(false).build();
        newDealProduct = newDealProduct.toBuilder().setIsInDeal(true).build();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
            products = products.toBuilder().setProducts(dealProduct.getId(), dealProduct).setProducts(newDealProduct.getId(), newDealProduct).build();
            products.writeTo(fos);
        }catch (IOException e) {
            System.out.println("Exception at Deal of moment");
        }
    }
    public static List<Integer> updateProductStockAndGetProductIdList(List<ProductProto.Product> productsList, List<Integer> stocks) {
        List<Integer> productIds = new ArrayList<>();
        for(int i = 0;i<productsList.size();i++) {
            ProductProto.Product product = productsList.get(i);
            productIds.add(product.getId());
            int newStock = product.getStock() - stocks.get(i);
            product = product.toBuilder().setStock(newStock).build();
            products = products.toBuilder().setProducts(product.getId(), product).build();
        }
        return productIds;
    }
}
