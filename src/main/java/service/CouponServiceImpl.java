package service;

import model.Coupon;

public class CouponServiceImpl implements CouponService {
    @Override
    public Coupon getCouponByCouponCode(String couponCode) {
        return Coupon.builder().couponCode("OFR001").discountPercentage(10).build();
    }
}
