package com.zkart.screens.orderProduct;

import com.zkart.dto.OrderStatusDTO;
import com.zkart.model.CouponProto;
import com.zkart.model.OrderProto;
import com.zkart.model.ProductProto;
import com.zkart.repository.ZkartRepository;
import com.zkart.screens.myCoupons.MyCouponsView;
import com.zkart.screens.myOrders.MyOrdersView;
import com.zkart.screens.userLogin.LoginView;
import com.zkart.screens.viewProducts.ViewProducts;
import com.zkart.utils.BaseScreen;
import com.zkart.utils.exceptions.IncompleteCouponCreationException;
import com.zkart.utils.exceptions.IncompleteOrderException;
import com.zkart.utils.exceptions.InvalidCouponException;
import com.zkart.utils.exceptions.UnknownUserException;

import java.util.ArrayList;
import java.util.List;

public class OrderProductView extends BaseScreen {
    private OrderProductViewModel viewModel;

    public OrderProductView(){
        viewModel = new OrderProductViewModel();
    }
    @Override
    public void display() {
        try {
            header("Order Product");
            if(!ZkartRepository.isUserLogin) {
                navigate(new LoginView());
            }
            List<ProductProto.Product> products = new ArrayList<>();
            List<Integer> stocks = new ArrayList<>();
            while(true) {
                System.out.println("1 -> Book Product By Id :");
                System.out.println("2 -> Search for product :");
                System.out.println("3 -> Back");
                int opt = getInt("Select Option :");
                if(opt == 1) {
                    int id = getInt("Enter Product Id :");
                    ProductProto.Product product = ZkartRepository.getProductById(id);
                    if(product.getStock() != 0){
                        int stock = getInt("Enter Count :");
                        if(stock > 0 && stock <= product.getStock()) {
                            products.add(product);
                            stocks.add(stock);
                        }else {
                            alert("Stock Unavailable");
                            continue;
                        }
                    }else {
                        alert("Product Unavailable");
                        continue;
                    }
                    if(getBoolean("Do you want to add more ?")) {
                        continue;
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
                                    navigate(new MyCouponsView());
                                } else {
                                    break;
                                }
                            }
                        }
                            try {
                                if (!getBoolean("Do you want to confirm Order ?")){
                                    return;
                                }
                                printOrders(viewModel.orderProducts(products, stocks, couponId)) ;
                                return;
                            }catch (InvalidCouponException e) {
                                alert(e.getMessage());
                            }
                } else if (opt == 2) {
                    navigate(new ViewProducts());
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
        catch (IncompleteCouponCreationException | UnknownUserException | IncompleteOrderException e) {
            alert(e.getMessage());
        }catch (Exception e) {
            alert(e.getMessage());
            display();
        }
    }

    public void printOrders(OrderStatusDTO order) {
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
