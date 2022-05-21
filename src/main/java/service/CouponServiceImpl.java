package service;

import lombok.RequiredArgsConstructor;
import model.Coupon;
import repository.CouponRepository;

@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon getCouponByCouponCode(String couponCode) {
        Coupon coupon = couponRepository.getCouponByCouponCode(couponCode);
        return coupon != null ? coupon : couponRepository.getCouponByCouponCode("NA");
    }
}
