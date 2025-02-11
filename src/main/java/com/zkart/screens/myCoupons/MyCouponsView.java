package com.zkart.screens.myCoupons;

import com.zkart.model.CouponProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.utils.BaseScreen;

import java.util.List;

public class MyCouponsView extends BaseScreen {
    @Override
    public void display() {
        if(!ZkartRepository.isUserLogin) {
            new LoginView();
        }
        if(ZkartRepository.isUserLogin) {
            header("My Coupons");

            // show available coupons;
            System.out.println("---------------------------------------------");

            List<CouponProto.Coupon> couponList = ZkartRepository.getUserCoupons();
            if(couponList.isEmpty()) {
                alert("No coupons Available");
                return;
            }
            for(var coupon : couponList) {
                printCoupon(coupon);
            }
            System.out.println("---------------------------------------------");
        }
    }
    public void printCoupon(CouponProto.Coupon coupon) {
        System.out.println("=================================================");
        System.out.print("Coupon Code :" +coupon.getId()+"     ");
        System.out.println("Discount Percent :"+ coupon.getDiscountPercent()+"%");
        System.out.print("Issued Date :"+coupon.getDateIssued()+"    ");
        System.out.println("Valid till :"+coupon.getValidTill());
        System.out.println("Obtained from Order :"+coupon.getIssuedForOrderId());
        if(coupon.getIsUsed()) {
            System.out.println("Coupon Status : Not Available");
            System.out.println("Coupon used on OrderId :"+coupon.getUsedOnOrderId());
        }else {
            System.out.println("Coupon status : Available");
        }
        System.out.println("=================================================");
        System.out.println();
    }
}
