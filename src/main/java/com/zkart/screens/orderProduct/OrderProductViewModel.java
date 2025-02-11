package com.zkart.screens.orderProduct;

import com.zkart.model.CouponProto;
import com.zkart.dto.OrderStatusDTO;
import com.zkart.model.OrderProto;
import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.viewProducts.ViewProducts;
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
        int dealProductId = ViewProducts.getDealProductId();
        int userId = ZkartRepository.loggedInUser.getId();
        int total = 0;
        int couponDiscount = 0;
        int finalPrice = 0;
        for(int i = 0;i<products.size();i++) {
            ProductProto.Product product = products.get(i);
            if(product.getId() == dealProductId) {
                total += (product.getPrice() - (product.getPrice()/10)) * stock.get(i);
            }else {
                total += product.getPrice() * stock.get(i);

            }
        }
        List< OrderProto.Order> userOrderList = ZkartRepository.getUserOrders(userId);
        int orderId = ZkartRepository.getOrdersSize()+100;
        if(userOrderList.size() == 3 || total >= 20000) {
            if(!ZkartRepository.createCoupon(userId, orderId)) {
                throw new IncompleteCouponCreationException();
            }
            System.out.println("****** Coupon Obtained For this order *******");
            System.out.println("Check My Coupons");
        }
        if(couponCode != null) {
            CouponProto.Coupon coupon = ZkartRepository.getCoupon(couponCode);
            if(coupon.getIsUsed()) {
                throw new InvalidCouponException();
            }
            couponDiscount = coupon.getDiscountPercent();
            finalPrice = (int)(total - ((total*couponDiscount)/100));
            if(ZkartRepository.createOrder(orderId, userId, products, stock, total, finalPrice, coupon)){
                return new OrderStatusDTO(true, userId, true, couponCode, couponDiscount ,total, finalPrice);
            }
            throw new IncompleteOrderException();
        }

        if(ZkartRepository.createOrder(orderId, userId, products, stock, total)){
            return new OrderStatusDTO(true, userId, false, null,0,total, finalPrice);
        }

        throw new IncompleteOrderException();
    }
}
