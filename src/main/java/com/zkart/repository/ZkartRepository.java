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

     static ProductProto.Products products;
     static OrderProto.Orders orders;
    public static AdminProto.Admin admin;
     static UserProto.Users users;
     static CouponProto.Coupons coupons;

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
                if(admin.getAdminUser().getEmail().isEmpty()) {
                    throw new Exception("");
                }
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
                if(products.getProductsCount() == 0) {
                    throw new Exception();
                }
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
                if(users.getUsersCount() == 0) {
                    throw new Exception();
                }
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
    private static ZkartRepository instance;


    public static ProductProto.Product getProductById(int id) throws Exception{
       return ProductRepository.getProductById(id);
    }

    public static boolean validateUser(String email, String password) throws InvalidCredentialsException {
       return UserRepository.validateUser(email, password);
    }
    public static boolean validateAdmin(String email, String password)  throws InitialAdminLoginException, InvalidCredentialsException {
        return UserRepository.validateAdmin(email, password);
    }
    public static void logout(){
        isAdminLogin = false;
        isUserLogin = false;
    }

    public static boolean userSignIn(String name, String email, String password) {
       return UserRepository.userSignIn(name, email,password);
    }
    public static boolean signInEmailValidation(String email) {
        return UserRepository.signInEmailValidation(email);
    }

    public static List<ProductProto.Product> getAllProducts(){
        return  products.getProductsList();
    }
    public static List<ProductProto.Product> searchProductOnCategory(String category) {
        return ProductRepository.searchProductOnCategory(category);
    }
    public static List<ProductProto.Product> searchProductOnName(String name) {
       return ProductRepository.searchProductOnName(name);
    }

    public static boolean updateUserPassword(String password, boolean isUser) {
        return UserRepository.updateUserPassword(password, isUser);
    }
    public static boolean addProduct(String category, String name, String description, String model, String brand, int price, int stock) throws Exception{
        return ProductRepository.addProduct(category, name, description, model, brand, price, stock);
    }
    public static boolean updateProductStock(int productId, int stock) throws Exception {
       return ProductRepository.updateProductStock(productId, stock);
    }
    public static boolean updateProductPrice(int productId, int price) throws Exception {
        return ProductRepository.updateProductPrice(productId, price);
    }
    public static boolean deleteProduct(int productId) throws Exception {
       return ProductRepository.deleteProduct(productId);
    }
    public static boolean updateProduct(int productId, String category, String name, String description, String model, String brand, int price, int stock) throws Exception {
        return ProductRepository.updateProduct(productId, category, name, description,model, brand, price, stock);
    }
    public static UserProto.User getUserById(int userId) {
        return users.getUsers(userId);
    }
    public static List<ProductProto.Product> getCriticalStockProducts() {
        return ProductRepository.getCriticalStockProducts();
    }
    public static boolean createOrder(int orderId, int userId, List<ProductProto.Product> productsList, List<Integer> stocks,List<OrderProto.ProductDetails> productDetailsList, int totalPrice) {
        return OrderRepository.createOrder(orderId, userId, productsList,  stocks, productDetailsList, totalPrice);
    }
    public static boolean createOrder(int orderId, int userId, List<ProductProto.Product> productsList, List<Integer> stocks,List<OrderProto.ProductDetails> productDetailsList, int totalPrice, int finalPrice, CouponProto.Coupon coupon) throws InvalidCouponException {
        return OrderRepository.createOrder( orderId, userId, productsList, stocks, productDetailsList, totalPrice,finalPrice, coupon);
    }
    public static boolean createCoupon(int userId, int orderId) {
        return OrderRepository.createCoupon(userId, orderId);
    }
    public static int getCouponIndex(String couponId) throws InvalidCouponException {
        return OrderRepository.getCouponIndex(couponId);
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
       return ProductRepository.updateProductStockAndGetProductIdList(productsList,stocks);
    }
}
