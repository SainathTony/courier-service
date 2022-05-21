package service;

import model.Coupon;

public interface CouponService {
    Coupon getCouponByCouponCode(String couponCode);
}
