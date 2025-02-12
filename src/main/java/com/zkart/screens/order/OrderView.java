package com.zkart.screens.order;

import com.zkart.dto.OrderStatusDTO;
import com.zkart.model.CouponProto;
import com.zkart.model.OrderProto;
import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.product.ProductView;
import com.zkart.screens.user.UserView;

import com.zkart.utils.BaseScreen;
import com.zkart.utils.exceptions.InvalidCouponException;

import java.util.ArrayList;
import java.util.List;

public class OrderView extends BaseScreen {
    public void userDisplay() {
        try {
            header("Order Product");
            if(!ZkartRepository.isUserLogin) {
                UserView.getInstance().login();
            }
            List<ProductProto.Product> products = new ArrayList<>();
            List<Integer> stocks = new ArrayList<>();
            while(true) {
                System.out.println("1 -> Book Product By Id :");
                System.out.println("2 -> Search for product :");
                System.out.println("3 -> Back");
                int opt = getInt("Select Option :");
                if(opt == 1) {
                   createOrder(products, stocks);
                } else if (opt == 2) {
                   ProductView.getInstance().viewAllProductsAdmin();
                }else if(opt == 3) {
                    break;
                }else {
                    alert("Invalid Option.");
                    if(!getBoolean("Do you want to continue ?")) {
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            alert(e.getMessage());
        }
    }
    private static OrderView instance;
    public static OrderView getInstance() {
        if(instance == null) {
            instance = new OrderView();
        }
        return instance;
    }
    private OrderViewModel viewModel;
    private OrderView(){
        viewModel = new OrderViewModel();
    }
    public static void printOrders(OrderProto.Order order) throws Exception{
        System.out.println("=======================================================");
        System.out.println("Invoice Number :"+order.getId());
        System.out.println("Total Price :"+order.getTotalPrice());
        if(order.getIsCouponApplied()) {
            System.out.println("Applied Coupon : true");
            System.out.println("Coupon Code :"+order.getCouponCode());
            System.out.println("Discount Percent :"+ order.getDiscountPercent());
            System.out.println("Final Price :"+(order.getTotalPrice() - ((order.getTotalPrice()*order.getDiscountPercent())/100)));
        }
        System.out.println("Products :");
        var tempList = order.getProductDetailsListList();

        for(int i = 0;i<tempList.size();i++ ) {
            ProductView.printProduct(
                    ZkartRepository.getProductById(tempList.get(i).getProductId()),
                    tempList.get(i).getCount(),
                    tempList.get(i).getIsInDeal(),
                    tempList.get(i).getDealPercent());
        }
        System.out.println("=======================================================");
        System.out.println();
    }
    public static void printCoupon(CouponProto.Coupon coupon) {
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
    public void createOrder(List<ProductProto.Product> products, List<Integer> stocks){
        try {
                int id = getInt("Enter Product Id :");
                ProductProto.Product product = ZkartRepository.getProductById(id);
                if(product.getStock() != 0){
                    int stock = getInt("Enter Count :");
                    if(stock > 0 && stock <= product.getStock()) {
                        products.add(product);
                        stocks.add(stock);
                    }else {
                        alert("Stock Unavailable");
                        return;
                    }
                }else {
                    alert("Product Unavailable");
                    return;
                }
                if(getBoolean("Do you want to add more ?")) {
                    return;
                }
                boolean couponAvail = getBoolean("Do you want to apply any coupon? ");
                String couponId = null;
                if(couponAvail) {
                    while (true) {
                        System.out.println("1 -> Enter Coupon Id");
                        System.out.println("2 -> Go to my Coupons");
                        System.out.println("3 -> Back");
                        int tempOpt = getInt("Choose Option :");
                        if (tempOpt == 1) {
                            couponId = getString("Enter coupon Id :");
                            CouponProto.Coupon coupon = ZkartRepository.getCoupon(couponId);
                            if (coupon == null) {
                                alert("Invalid Coupon");
                                continue;
                            }
                            System.out.println("Coupon Code :" + coupon.getId());
                            System.out.println("Coupon Discount :" + coupon.getDiscountPercent() + "%");

                            alert("Coupon Applied Successfully");
                        } else if (tempOpt == 2) {
                            UserView.getInstance().myCoupons();
                        } else {
                            break;
                        }
                    }
                }
                try {
                    if (!getBoolean("Do you want to confirm Order ?")){
                        return;
                    }
                    printOrderStatus(viewModel.orderProducts(products, stocks, couponId)) ;
                }catch (InvalidCouponException e) {
                    alert(e.getMessage());
                }


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void printOrderStatus(OrderStatusDTO order) {
        if(!order.isOrderSuccess) {
            alert("Order Failed");
            return;
        }
        System.out.println("=======================================================");
        System.out.println("Order Status : Confirmed" );
        System.out.println("Total Price :"+order.totalPrice);
        if(order.isCouponCodeApplied) {
            System.out.println("Coupon Code :"+order.appliedCouponCode);
            System.out.println("Discount Percent :"+order.couponCodeDiscountPercent);
            System.out.println("Discount Price :"+ (order.totalPrice - order.finalPrice));
            System.out.println("Final Price :"+order.finalPrice);
        }
        System.out.println("=======================================================");
        System.out.println();
    }
}
