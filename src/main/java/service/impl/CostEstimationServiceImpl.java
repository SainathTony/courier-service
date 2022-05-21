package service.impl;

import lombok.RequiredArgsConstructor;
import model.CostEstimate;
import model.Coupon;
import model.CourierPackage;
import service.CostEstimationService;
import service.CouponService;
import service.InputService;

@RequiredArgsConstructor
public class CostEstimationServiceImpl implements CostEstimationService {

    private final InputService inputService;
    private final CouponService couponService;

    @Override
    public CostEstimate estimate(CourierPackage courierPackage) {
        double totalCost = getTotalCost(courierPackage);
        Coupon coupon = couponService.getCouponByCouponCode(courierPackage.getCouponCode());
        boolean couponApplicable = coupon.getOfferCriteria().isCouponApplicableFor(courierPackage);
        double discount = couponApplicable ? applyCoupon(coupon, totalCost) : 0;
        return getEstimateWith(courierPackage, totalCost, discount);
    }

    private CostEstimate getEstimateWith(CourierPackage courierPackage, double totalCost, double discount) {
        return CostEstimate.builder().packageId(courierPackage.getPackageId()).discount(discount).totalCost(totalCost - discount).build();
    }

    private double getTotalCost(CourierPackage courierPackage) {
        double totalCost = inputService.getBaseDeliveryCost() + (courierPackage.getPackageWeight() * 10) + (courierPackage.getDeliveryDistance() * 5);
        return totalCost;
    }

    private double applyCoupon(Coupon coupon, double totalCost) {
        return (coupon.getDiscountPercentage() / (double) 100) * totalCost;
    }
}
