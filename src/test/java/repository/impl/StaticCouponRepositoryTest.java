package repository.impl;

import model.Coupon;
import org.junit.jupiter.api.Test;
import repository.CouponRepository;
import offers.impl.OfferByWeightAndDistance;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StaticCouponRepositoryTest {

    private CouponRepository couponRepository = new StaticCouponRepository();

    @Test
    void shouldReturnCouponByCouponCode() {
        Coupon expectedCoupon = Coupon.builder().couponCode("OFR001").discountPercentage(10)
                .offerCriteria(new OfferByWeightAndDistance(0, 200, 70, 200)).build();

        Coupon coupon = couponRepository.getCouponByCouponCode("OFR001");

        assertEquals(expectedCoupon, coupon);
    }

    @Test
    void shouldReturnDefaultCoupon() {
        Coupon expectedCoupon = Coupon.builder().couponCode("NA").discountPercentage(0)
                .offerCriteria(new OfferByWeightAndDistance(0,0,0,0)).build();

        Coupon coupon = couponRepository.getCouponByCouponCode("NA");

        assertEquals(expectedCoupon, coupon);
    }

    @Test
    void shouldReturnNullForInvalidCouponCode() {

        Coupon coupon = couponRepository.getCouponByCouponCode("XYZ");

        assertEquals(null, coupon);
    }
}