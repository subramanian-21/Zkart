package com.zkart.repository;

import com.zkart.model.BaseUserProto;
import com.zkart.model.CouponProto;
import com.zkart.model.UserProto;
import com.zkart.utils.PasswordHandler;
import com.zkart.utils.exceptions.InitialAdminLoginException;
import com.zkart.utils.exceptions.InvalidCredentialsException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static boolean validateUser(String email, String password) throws InvalidCredentialsException {
        List<UserProto.User> tempList = ZkartRepository.users.getUsersList();
        for (UserProto.User user : tempList) {

            String encrypt = PasswordHandler.encryptPassword(password);
            if(user.getUserDetails().getEmail().equals(email) && user.getUserDetails().getPassword().equals(encrypt)) {
                ZkartRepository.loggedInUser = user;
                ZkartRepository.isAdminLogin = false;
                return ZkartRepository.isUserLogin = true;
            }
        }
        ZkartRepository.loggedInUser = null;
        throw new InvalidCredentialsException();
    }
    public static boolean validateAdmin(String email, String password)  throws InitialAdminLoginException, InvalidCredentialsException {
        boolean temp = false;

        if(ZkartRepository.admin.getAdminUser().getPassword().equals(PasswordHandler.encryptPassword(ZkartRepository.DEFAULT_ADMIN_PASSWORD)) && password.equals(ZkartRepository.DEFAULT_ADMIN_PASSWORD)) {
            System.out.println("Initial admin login");
            ZkartRepository.isAdminLogin = true;
            ZkartRepository.isUserLogin = false;
            throw new InitialAdminLoginException();
        }
        temp = ZkartRepository.admin.getAdminUser().getEmail().equals(email)
                && ZkartRepository.admin.getAdminUser().getPassword().equals(PasswordHandler.encryptPassword(password));

        if(temp) {
            ZkartRepository.isAdminLogin = true;
            ZkartRepository.isUserLogin = false;
            return true;
        }else {
            ZkartRepository.isAdminLogin = false;
            ZkartRepository.isUserLogin = false;
        }
        throw new InvalidCredentialsException();
    }
    public static void logout(){
        ZkartRepository.isAdminLogin = false;
        ZkartRepository.isUserLogin = false;
    }

    public static boolean userSignIn(String name, String email, String password) {
        BaseUserProto.BaseUser temp = BaseUserProto.BaseUser.newBuilder().setId(ZkartRepository.users.getUsersCount())
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
        ZkartRepository.users = ZkartRepository.users.toBuilder().addUsers(user).build();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH+"user_db.txt"));
            ZkartRepository.users.writeTo(fos);
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
        List<UserProto.User> userList = ZkartRepository.users.getUsersList();

        for(UserProto.User user : userList) {
            if(user.getUserDetails().getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    public static List<CouponProto.Coupon> getUserCoupons() {
        List<CouponProto.Coupon> couponList = new ArrayList<>();

        for(CouponProto.Coupon c : ZkartRepository.coupons.getCouponsList()) {
            if(c.getUserId() == ZkartRepository.loggedInUser.getUserDetails().getId()) {
                couponList.add(c);
            }
        }
        return couponList;
    }
    public static boolean updateUserPassword(String password, boolean isUser) {
        if(isUser) {
            if(!ZkartRepository.isUserLogin) {
                return false;
            }
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "user_db.txt"));
                BaseUserProto.BaseUser temp = ZkartRepository.loggedInUser.getUserDetails();
                temp = temp.toBuilder().setPassword(PasswordHandler.encryptPassword(password)).addPrePasswords(PasswordHandler.encryptPassword(password)).build();
                ZkartRepository.loggedInUser = ZkartRepository.loggedInUser.toBuilder().setUserDetails(temp).build();
                ZkartRepository.users = ZkartRepository.users.toBuilder().setUsers(ZkartRepository.loggedInUser.getUserDetails().getId(), ZkartRepository.loggedInUser).build();
                ZkartRepository.users.writeTo(fos);
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
        else  {
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "admin_db.txt"));
                BaseUserProto.BaseUser user = ZkartRepository.admin.getAdminUser();
                user = user.toBuilder().setPassword(PasswordHandler.encryptPassword(password)).addPrePasswords(PasswordHandler.encryptPassword(password)).build();
                ZkartRepository.admin = ZkartRepository.admin.toBuilder().setAdminUser(user).build();
                ZkartRepository.admin.writeTo(fos);
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

    }

}
