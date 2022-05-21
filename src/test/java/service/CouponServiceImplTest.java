package service;

import model.Coupon;
import org.junit.jupiter.api.Test;
import repository.CouponRepository;
import repository.impl.StaticCouponRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CouponServiceImplTest {

    private final CouponRepository couponRepository = mock(StaticCouponRepository.class);
    private final CouponService couponService = new CouponServiceImpl(couponRepository);

    @Test
    void shouldGetCouponByCouponCode() {
        Coupon expectedCoupon = Coupon.builder().couponCode("OFR001").discountPercentage(10).build();
        when(couponRepository.getCouponByCouponCode("OFR001")).thenReturn(expectedCoupon);

        Coupon coupon = couponService.getCouponByCouponCode("OFR001");

        assertEquals(expectedCoupon, coupon);
    }

    @Test
    void shouldGetDefaultCouponForInvalidCouponCode() {
        Coupon expectedCoupon = Coupon.builder().couponCode("NA").discountPercentage(0).build();
        when(couponRepository.getCouponByCouponCode("XYZ")).thenReturn(null);
        when(couponRepository.getCouponByCouponCode("NA")).thenReturn(expectedCoupon);

        Coupon coupon = couponService.getCouponByCouponCode("XYZ");

        assertEquals(expectedCoupon, coupon);
        verify(couponRepository, times(1)).getCouponByCouponCode("XYZ");
        verify(couponRepository, times(1)).getCouponByCouponCode("NA");
    }
}