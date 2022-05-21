package service.impl;

import lombok.RequiredArgsConstructor;
import model.Coupon;
import repository.CouponRepository;
import service.CouponService;

@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon getCouponByCouponCode(String couponCode) {
        Coupon coupon = couponRepository.getCouponByCouponCode(couponCode);
        return coupon != null ? coupon : couponRepository.getCouponByCouponCode("NA");
    }
}
