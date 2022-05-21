package repository;

import model.Coupon;

public interface CouponRepository {
    Coupon getCouponByCouponCode(String couponCode);
}
