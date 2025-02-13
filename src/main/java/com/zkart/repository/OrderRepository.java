package com.zkart.repository;

import com.zkart.model.CouponProto;
import com.zkart.model.OrderProto;
import com.zkart.model.ProductProto;
import com.zkart.utils.DateHandler;
import com.zkart.utils.UniqueId;
import com.zkart.utils.exceptions.InvalidCouponException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class OrderRepository {
    public static boolean createOrder(int orderId, int userId, List<ProductProto.Product> productsList, List<Integer> stocks, List<OrderProto.ProductDetails> productDetailsList, int totalPrice) {
        List<Integer> productIds = ZkartRepository.updateProductStockAndGetProductIdList(productsList, stocks);
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
            fosOrder = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "order_db.txt"));
            fosProduct = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt"));
            ZkartRepository.orders = ZkartRepository.orders.toBuilder().addOrders(order).build();
            ZkartRepository.orders.writeTo(fosOrder);
            ZkartRepository.products.writeTo(fosProduct);
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
        List<Integer> productIds = ZkartRepository.updateProductStockAndGetProductIdList(productsList, stocks);
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
            fosOrder = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "order_db.txt"));
            fosProduct = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "product_db.txt"));
            fosCoupon = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH +"coupon_db.txt"));
            coupon = coupon.toBuilder().setIsUsed(true).setUsedOnOrderId(orderId).build();
            ZkartRepository.coupons = ZkartRepository.coupons.toBuilder().setCoupons(getCouponIndex(coupon.getId()), coupon).build();
            ZkartRepository.orders = ZkartRepository.orders.toBuilder().addOrders(order).build();
            ZkartRepository.orders.writeTo(fosOrder);
            ZkartRepository.products.writeTo(fosProduct);
            ZkartRepository.coupons.writeTo(fosCoupon);
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
            fosCoupon = new FileOutputStream(new File(ZkartRepository.DB_FILE_ROOT_PATH + "coupon_db.txt"));
            CouponProto.Coupon coupon = CouponProto
                    .Coupon
                    .newBuilder()
                    .setId(UniqueId.getCouponId(userId))
                    .setDateIssued(DateHandler.getCurrentDate())
                    .setValidTill(DateHandler.getDateAfter(ZkartRepository.COUPON_EXPIRATION_DAYS))
                    .setUserId(userId)
                    .setDiscountPercent(UniqueId.generateRandomInt(ZkartRepository.COUPON_DISCOUNT_PERCENT_START, ZkartRepository.COUPON_DISCOUNT_PERCENT_END))
                    .setIsUsed(false)
                    .setIssuedForOrderId(orderId)
                    .build();
            ZkartRepository.coupons = ZkartRepository.coupons.toBuilder().addCoupons(coupon).build();
            ZkartRepository.coupons.writeTo(fosCoupon);
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
        List<CouponProto.Coupon> couponList = ZkartRepository.coupons.getCouponsList();
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
}
