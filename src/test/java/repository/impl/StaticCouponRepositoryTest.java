package repository.impl;

import model.Coupon;
import org.junit.jupiter.api.Test;
import repository.CouponRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StaticCouponRepositoryTest {

    private CouponRepository couponRepository = new StaticCouponRepository();

    @Test
    void shouldReturnCouponByCouponCode() {
        Coupon expectedCoupon = Coupon.builder().couponCode("OFR001").discountPercentage(10).build();

        Coupon coupon = couponRepository.getCouponByCouponCode("OFR001");

        assertEquals(expectedCoupon, coupon);
    }
}