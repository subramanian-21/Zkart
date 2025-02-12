package com.zkart.repository;

import com.zkart.model.*;

import com.zkart.utils.DateHandler;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.UniqueId;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCouponException;
import com.zkart.utils.exceptions.InvalidCredentialsException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ZkartRepository {
    public static final String DEFAULT_ADMIN_PASSWORD = "12345";
    public static final String DB_FILE_ROOT_PATH = "/Users/subramani-22949/IdeaProjects/z-kart/src/main/java/com/zkart/dbFiles/";
    public static final  int COUPON_EXPIRATION_DAYS = 1;
    public static final  int COUPON_DISCOUNT_PERCENT_START = 20;
    public static final  int COUPON_DISCOUNT_PERCENT_END = 30;
    public static final int PRODUCT_CRITICAL_COUNT = 10;

    private static ProductProto.Products products;
    private static OrderProto.Orders orders;
    public static AdminProto.Admin admin;
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
            }
            catch (Exception e) {
                BaseUserProto.BaseUser tempUser = BaseUserProto.BaseUser.newBuilder().setId(0)
                        .setFullname("admin")
                        .setEmail("admin@zoho.com")
                        .setPassword(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD))
                        .addPrePasswords(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD)).build();

                admin = AdminProto
                        .Admin
                        .newBuilder()
                        .setAdminUser(tempUser)
                        .setUserType(AdminProto.AdminUserType.SUPER_USER)
                        .build();

                adminFos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "admin_db.txt"));
                admin.writeTo(adminFos);
            }
            finally {
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
                                .setAddedAt(DateHandler.getTimeStamp())
                                .build();
                        productList.add(productProto);
                    }
                    products = ProductProto.Products.newBuilder().addAllProducts(productList).build();
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
                FileOutputStream userFos = null;
                BufferedReader userBr = null;
                List<UserProto.User> userList = new ArrayList<>();
                try{
                userFos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "user_db.txt"));
                userBr = new BufferedReader(new FileReader(new File(DB_FILE_ROOT_PATH + "default_users.txt")));
                String user = null;
                while (userBr.ready()) {
                    user = userBr.readLine();
                    String[] userArr = user.split("\\|");
                    BaseUserProto.BaseUser temp = BaseUserProto.BaseUser.newBuilder().setId(Integer.parseInt(userArr[0]))
                            .setEmail(userArr[1])
                            .setPassword(userArr[2])
                            .setFullname(userArr[3])
                            .build();
                    UserProto.User userProto = UserProto
                            .User
                            .newBuilder()
                            .setUserDetails(temp)
                            .build();

                    userList.add(userProto);
                }
                users = UserProto.Users.newBuilder().addAllUsers(userList).build();
                users.writeTo(userFos);

            }catch (Exception e1) {
                e1.printStackTrace();
            }finally {
                if(userBr != null) {
                    try{
                        userBr.close();
                    }catch (Exception e2) {
                        throw new RuntimeException("Error Occured in Buffered Reader");
                    }
                }
                if(userFos != null) {
                    try {
                        userFos.close();
                    }catch (Exception e2) {
                        throw new RuntimeException("Error occured in Product FIS");
                    }
                }
            }
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
    public static ProductProto.Product getProductById(int id) throws Exception{
        if(id < 0 || id >= products.getProductsCount()) {
            throw new Exception("Invalid Product Id");
        }
        return products.getProducts(id);
    }

    public static boolean validateUser(String email, String password) throws InvalidCredentialsException {
        List<UserProto.User> tempList = users.getUsersList();
        for (UserProto.User user : tempList) {

                String encrypt = PasswordHandler.encryptPassword(password);
                if(user.getUserDetails().getEmail().equals(email) && user.getUserDetails().getPassword().equals(encrypt)) {
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

        if(admin.getAdminUser().getPassword().equals(PasswordHandler.encryptPassword(DEFAULT_ADMIN_PASSWORD)) && password.equals(DEFAULT_ADMIN_PASSWORD)) {
            System.out.println("Initial admin login");
            isAdminLogin = true;
            isUserLogin = false;
            throw new InitialAdminLoginException();
        }
        temp = admin.getAdminUser().getEmail().equals(email)
                && admin.getAdminUser().getPassword().equals(PasswordHandler.encryptPassword(password));

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
        BaseUserProto.BaseUser temp = BaseUserProto.BaseUser.newBuilder().setId(users.getUsersCount())
                .setFullname(name)
                .setEmail(email)
                .setPassword(PasswordHandler.encryptPassword(password))
                .addPrePasswords(PasswordHandler.encryptPassword(password))
                .build();
        UserProto.User user = UserProto
                .User
                .newBuilder()
                .setUserDetails(temp)
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
            if(user.getUserDetails().getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public static List<ProductProto.Product> getAllProducts(){
        return  products.getProductsList();
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
                .filter(pro -> pro.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static boolean updateAdminPassword(String password) {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "admin_db.txt"));
            BaseUserProto.BaseUser user = admin.getAdminUser();
            user = user.toBuilder().setPassword(PasswordHandler.encryptPassword(password)).addPrePasswords(PasswordHandler.encryptPassword(password)).build();
            admin = admin.toBuilder().setAdminUser(user).build();
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
        return true;
    }
    public static boolean updateUserPassword(String password) {
        if(!isUserLogin) {
            return false;
        }
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "user_db.txt"));
            BaseUserProto.BaseUser temp = loggedInUser.getUserDetails();
            temp = temp.toBuilder().setPassword(PasswordHandler.encryptPassword(password)).addPrePasswords(PasswordHandler.encryptPassword(password)).build();
            loggedInUser = loggedInUser.toBuilder().setUserDetails(temp).build();
            users = users.toBuilder().setUsers(loggedInUser.getUserDetails().getId(), loggedInUser).build();
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

       if(isAdminLogin) {

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
                   .setAddedBy(admin.getAdminUser().getEmail())
                   .setAddedAt(DateHandler.getTimeStamp())
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
        if(stock < 0) {
            throw new Exception("Invalid Stock Count");
        }
        if(isAdminLogin) {

            ProductProto.Product product = products.getProducts(productId).toBuilder().setStock(stock).setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(admin.getAdminUser().getEmail()).build();
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

        if(isAdminLogin) {

            ProductProto.Product product = products.getProducts(productId).toBuilder().setPrice(price).setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(admin.getAdminUser().getEmail()).build();
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
    public static boolean deleteProduct(int productId) throws Exception {
        if(isAdminLogin) {

            ProductProto.Product product = products.getProducts(productId).toBuilder().setStock(-1).setUpdatedAt(DateHandler.getTimeStamp())
                    .setUpdatedBy(admin.getAdminUser().getEmail()).build();
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
        if(price < 0 || stock < 0) throw new Exception("Invalid Price or count");
        if(isAdminLogin) {

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
                    .setUpdatedBy(admin.getAdminUser().getEmail())
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
    public static boolean createOrder(int orderId, int userId, List<ProductProto.Product> productsList, List<Integer> stocks,List<OrderProto.ProductDetails> productDetailsList, int totalPrice) {
        List<Integer> productIds = updateProductStockAndGetProductIdList(productsList, stocks);
        OrderProto.Order order = OrderProto
                .Order
                .newBuilder()
                .setUserId(userId)
                .setTotalPrice(totalPrice)
                .setFinalPrice(totalPrice)
                .addAllProductDetailsList(productDetailsList)
                .setIsCouponApplied(false)
                .setId(orderId)
                .setTimeStamps(DateHandler.getTimeStamp())
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
    public static boolean createOrder(int orderId, int userId, List<ProductProto.Product> productsList, List<Integer> stocks,List<OrderProto.ProductDetails> productDetailsList, int totalPrice, int finalPrice, CouponProto.Coupon coupon) throws InvalidCouponException {
        List<Integer> productIds = updateProductStockAndGetProductIdList(productsList, stocks);
        OrderProto.Order order = OrderProto
                .Order
                .newBuilder()
                .setUserId(userId)
                .setTotalPrice(totalPrice)
                .setFinalPrice(totalPrice)
                .addAllProductDetailsList(productDetailsList)
                .setIsCouponApplied(false)
                .setCouponCode(coupon.getId())
                .setDiscountPercent(coupon.getDiscountPercent())
                .setFinalPrice(finalPrice)
                .setTimeStamps(DateHandler.getTimeStamp())
                .setId(orderId)
                .build();
        FileOutputStream fosOrder = null;
        FileOutputStream fosProduct = null;
        FileOutputStream fosCoupon = null;
        try {
            fosOrder = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "order_db.txt"));
            fosProduct = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
            fosCoupon = new FileOutputStream(new File(DB_FILE_ROOT_PATH +"coupon_db.txt"));
            coupon = coupon.toBuilder().setIsUsed(true).setUsedOnOrderId(orderId).build();
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
                    throw new RuntimeException("Exception in FOS Product");
                }
            }
            if(fosCoupon != null) {
                try {
                    fosCoupon.close();
                }catch (IOException e) {
                    throw new RuntimeException("Exception in FOS coupon");
                }
            }
        }
        return true;
    }
    public static boolean createCoupon(int userId, int orderId) {
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
                        .setIsUsed(false)
                        .setIssuedForOrderId(orderId)
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
    public static int getOrdersSize() {
        return ZkartRepository.orders.getOrdersCount();
    }
    public static List<CouponProto.Coupon> getUserCoupons() {
        List<CouponProto.Coupon> couponList = new ArrayList<>();

        for(CouponProto.Coupon c : coupons.getCouponsList()) {
            if(c.getUserId() == loggedInUser.getUserDetails().getId()) {
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
