package repository.impl;

import model.Coupon;
import repository.CouponRepository;

import java.util.HashMap;
import java.util.Map;

public class StaticCouponRepository implements CouponRepository {

    private final Map<String, Coupon> coupons = new HashMap<>();

    public StaticCouponRepository() {
        coupons.put("OFR001", Coupon.builder().couponCode("OFR001").discountPercentage(10).build());
        coupons.put("OFR002", Coupon.builder().couponCode("OFR002").discountPercentage(7).build());
        coupons.put("OFR003", Coupon.builder().couponCode("OFR003").discountPercentage(5).build());
        coupons.put("NA", Coupon.builder().couponCode("NA").discountPercentage(0).build());
    }

    @Override
    public Coupon getCouponByCouponCode(String couponCode) {
        Coupon coupon = coupons.get(couponCode);
        return coupon != null ? coupon : coupons.get("NA");
    }
}
