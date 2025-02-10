package com.zkart.screens.orderProduct;

import com.zkart.model.CouponProto;
import com.zkart.dto.OrderStatusDTO;
import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.utils.exceptions.IncompleteCouponCreationException;
import com.zkart.utils.exceptions.IncompleteOrderException;
import com.zkart.utils.exceptions.InvalidCouponException;
import com.zkart.utils.exceptions.UnknownUserException;

import java.util.List;

public class OrderProductViewModel {
    public OrderStatusDTO orderProducts(List<ProductProto.Product> products, List<Integer> stock, String couponCode) throws IncompleteCouponCreationException, UnknownUserException, InvalidCouponException, IncompleteOrderException {
        if(!ZkartRepository.isUserLogin) {
            throw new UnknownUserException();
        }
        int userId = ZkartRepository.loggedInUser.getId();
        int total = 0;
        int couponDiscount = 0;
        int finalPrice = 0;
        for(int i = 0;i<products.size();i++) {
            ProductProto.Product product = products.get(i);
            if(product.getIsInDeal()) {
                total += (product.getPrice() - (product.getPrice()/10)) * stock.get(i);
            }else {
                total += product.getPrice() * stock.get(i);

            }
        }

        int userTransactionCount = ZkartRepository.loggedInUser.getTransactionCount()+1;
        int userTransactionAmount = ZkartRepository.loggedInUser.getTotalTransaction()+total;

        if(userTransactionAmount >= 2000 || userTransactionCount == 3) {
            if(!ZkartRepository.createCoupon(userId)) {
                System.out.println("Coupon Obtained");
                throw new IncompleteCouponCreationException();
            }
        }
        if(couponCode != null) {
            CouponProto.Coupon coupon = ZkartRepository.getCoupon(couponCode);

            if(coupon == null || coupon.getRemainingCount() == 0) {
                throw new InvalidCouponException();
            }
            couponDiscount = coupon.getDiscountPercent();
            finalPrice = (int)(total - ((total*couponDiscount)/100));
            if(ZkartRepository.createOrder(userId, products, stock, total, finalPrice, coupon)){
                return new OrderStatusDTO(true, userId, true, couponCode, couponDiscount ,total, finalPrice);
            }
            throw new IncompleteOrderException();
        }

        if(ZkartRepository.createOrder(userId, products, stock, total, userTransactionCount, userTransactionAmount)){
            return new OrderStatusDTO(true, userId, false, null,0,total, finalPrice);
        }

        throw new IncompleteOrderException();
    }
}
