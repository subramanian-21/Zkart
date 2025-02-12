package com.zkart.screens.order;

import com.zkart.dto.OrderStatusDTO;
import com.zkart.model.CouponProto;
import com.zkart.model.OrderProto;
import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.product.ProductViewModel;
import com.zkart.utils.exceptions.IncompleteCouponCreationException;
import com.zkart.utils.exceptions.IncompleteOrderException;
import com.zkart.utils.exceptions.InvalidCouponException;
import com.zkart.utils.exceptions.UnknownUserException;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel {
    public OrderStatusDTO orderProducts(List<ProductProto.Product> products, List<Integer> stock, String couponCode) throws IncompleteCouponCreationException, UnknownUserException, InvalidCouponException, IncompleteOrderException {
        if(!ZkartRepository.isUserLogin) {
            throw new UnknownUserException();
        }
        int dealProductId = ProductViewModel.getDealProductId();
        int userId = ZkartRepository.loggedInUser.getUserDetails().getId();
        int total = 0;
        int couponDiscount = 0;
        int finalPrice = 0;
        List<OrderProto.ProductDetails> productDetailsList = new ArrayList<>();
        for(int i = 0;i<products.size();i++) {
            ProductProto.Product product = products.get(i);
            OrderProto.ProductDetails productDetails = null;
            if(product.getId() == dealProductId) {
                total += (product.getPrice() - (product.getPrice()/10)) * stock.get(i);
                 productDetails = OrderProto
                        .ProductDetails
                        .newBuilder()
                        .setProductId(product.getId())
                        .setIsInDeal(true)
                        .setCount(stock.get(i))
                        .setDealPercent(10)
                        .build();

            }else {
                 productDetails = OrderProto
                        .ProductDetails
                        .newBuilder()
                        .setProductId(product.getId())
                        .setIsInDeal(false)
                        .setCount(stock.get(i))
                        .setDealPercent(0)
                        .build();
                total += product.getPrice() * stock.get(i);
            }
            productDetailsList.add(productDetails);
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
            if(ZkartRepository.createOrder(orderId, userId, products, stock,productDetailsList, total, finalPrice, coupon)){
                return new OrderStatusDTO(true, userId, true, couponCode, couponDiscount ,total, finalPrice);
            }
            throw new IncompleteOrderException();
        }

        if(ZkartRepository.createOrder(orderId, userId, products, stock, productDetailsList, total)){
            return new OrderStatusDTO(true, userId, false, null,0,total, finalPrice);
        }

        throw new IncompleteOrderException();
    }
}
