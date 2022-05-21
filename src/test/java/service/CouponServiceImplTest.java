package service;

import model.Coupon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CouponServiceImplTest {

    private final CouponService couponService = new CouponServiceImpl();

    @Test
    void shouldGetCouponByCouponCode() {
        Coupon expectedCoupon = Coupon.builder().couponCode("OFR001").discountPercentage(10).build();

        Coupon coupon = couponService.getCouponByCouponCode("OFR001");

        assertEquals(expectedCoupon, coupon);
    }
}