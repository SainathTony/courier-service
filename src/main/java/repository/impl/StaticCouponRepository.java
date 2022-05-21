package repository.impl;

import model.Coupon;
import repository.CouponRepository;
import service.impl.OfferByWeightAndDistance;

import java.util.HashMap;
import java.util.Map;

public class StaticCouponRepository implements CouponRepository {

    private final Map<String, Coupon> coupons = new HashMap<>();

    public StaticCouponRepository() {
        coupons.put("OFR001", Coupon.builder().couponCode("OFR001").discountPercentage(10)
                .offerCriteria(new OfferByWeightAndDistance(0, 200, 70, 200)).build());
        coupons.put("OFR002", Coupon.builder().couponCode("OFR002").discountPercentage(7)
                .offerCriteria(new OfferByWeightAndDistance(50, 150, 100, 250)).build());
        coupons.put("OFR003", Coupon.builder().couponCode("OFR003").discountPercentage(5)
                .offerCriteria(new OfferByWeightAndDistance(50, 250, 10, 150)).build());
        coupons.put("NA", Coupon.builder().couponCode("NA").discountPercentage(0)
                .offerCriteria(new OfferByWeightAndDistance(0, 0, 0, 0)).build());
    }

    @Override
    public Coupon getCouponByCouponCode(String couponCode) {
        return coupons.get(couponCode);
    }
}
