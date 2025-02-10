package com.zkart.dto;

public class OrderStatusDTO {
    public boolean isOrderSuccess;
    public int userId;
    public boolean isCouponCodeApplied;
    public String appliedCouponCode;
    public int couponCodeDiscountPercent;
    public int totalPrice;
    public int finalPrice;

    public OrderStatusDTO(boolean isOrderSuccess,int userId, boolean isCouponCodeApplied, String appliedCouponCode, int couponCodeDiscountPercent, int totalPrice, int finalPrice) {
        this.isOrderSuccess = isOrderSuccess;
        this.userId = userId;
        this.isCouponCodeApplied = isCouponCodeApplied;
        this.appliedCouponCode = appliedCouponCode;
        this.couponCodeDiscountPercent = couponCodeDiscountPercent;
        this.totalPrice = totalPrice;
        this.finalPrice = finalPrice;
    }
}
