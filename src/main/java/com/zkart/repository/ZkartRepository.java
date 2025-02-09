package com.zkart.repository;

import com.zkart.Main;
import com.zkart.model.*;
import com.zkart.screens.adminLogin.AdminLoginView;
import com.zkart.screens.changePassword.ChangePasswordView;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;
import com.zkart.utils.exceptions.InvalidPasswordSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ZkartRepository {
    public static final String DEFAULT_ADMIN_PASSWORD = "12345";
    public static final String DB_FILE_ROOT_PATH = "/Users/subramani-22949/IdeaProjects/z-kart/src/main/java/com/zkart/dbFiles/";
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

            try {
                products =  ProductProto.Products.parseFrom(productFis);
                System.out.println("products initialized...");
            }catch (Exception e) {
                FileOutputStream productFos = null;
                BufferedReader bufferedReader = null;

                List<ProductProto.Product> productList = new ArrayList<>();
                try {

                    productFos = new FileOutputStream(new File(DB_FILE_ROOT_PATH + "product_db.txt"));
                    bufferedReader = new BufferedReader(new FileReader(new File(DB_FILE_ROOT_PATH + "default_products.txt")));
                    String product = null;
                    int count = 0;
                    while (bufferedReader.ready()) {
                        count ++;
                        System.out.println(count);
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
            try {
                users = UserProto.Users.parseFrom(userFis);
            }catch (Exception e) {
                System.out.println("User Initializing for the first time");
            }
            try {
                coupons = CouponProto.Coupons.parseFrom(couponFis);
            }catch (Exception e) {
                System.out.println("Coupons Initializing for the first time");
            }
           try {
               orders = OrderProto.Orders.parseFrom(orderFis);
           }catch (Exception e) {
               System.out.println("Order Initialized for the first time");
           }

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
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
                .setTotalTransaction(0)
                .setTransactionCount(0)
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

    public static boolean createOrder(int userId, List<ProductProto.Product> productsList, List<Integer> stocks) throws IOException{
        int id = 0;

        try {
            id = orders.getOrdersCount();
        }catch (NullPointerException e) {
            System.out.println("NPE");
        }
        OrderProto.Order order = OrderProto
                .Order
                .newBuilder()
                .setUserId(userId)
                .setId(id+100)
                .build();
        int total = 0;
        for(int i = 0;i<productsList.size();i++) {
            ProductProto.Product product = productsList.get(i);
            total += product.getPrice() * stocks.get(i);
            order = order.toBuilder().addProductIds(product.getId()).build();
            int newStock = product.getStock() - stocks.get(i);
            product.toBuilder().setStock(newStock);
            products.toBuilder().setProducts(product.getId(), product);
        }
        order = order.toBuilder().setTotalPrice(total).build();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(DB_FILE_ROOT_PATH+"order_db.txt"));
            orders = orders.toBuilder().addOrders(order).build();
            orders.writeTo(fos);
        }catch (IOException e) {
            orders = OrderProto.Orders.newBuilder().addOrders(order).build();
            orders.writeTo(fos);
        }finally {
            if(fos != null) {
                fos.close();
            }
        }
        return true;
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


}
