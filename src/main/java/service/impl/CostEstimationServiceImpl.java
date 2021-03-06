package service.impl;

import lombok.RequiredArgsConstructor;
import model.DeliverySummary;
import model.Coupon;
import model.CourierPackage;
import service.CostEstimationService;
import service.CouponService;

@RequiredArgsConstructor
public class CostEstimationServiceImpl implements CostEstimationService {

    private final CouponService couponService;

    @Override
    public DeliverySummary getDeliveryCostWithOffer(CourierPackage courierPackage, double baseDeliveryCost) {
        double totalCost = getTotalCost(courierPackage, baseDeliveryCost);
        Coupon coupon = couponService.getCouponByCouponCode(courierPackage.getCouponCode());
        boolean couponApplicable = coupon.getOfferCriteria().isCouponApplicableFor(courierPackage);
        double discount = couponApplicable ? applyCoupon(coupon, totalCost) : 0;
        return getEstimateWith(courierPackage, totalCost, discount);
    }

    private DeliverySummary getEstimateWith(CourierPackage courierPackage, double totalCost, double discount) {
        return DeliverySummary.builder().packageId(courierPackage.getPackageId()).discount(roundTo2DecimalPlaces(discount)).totalCost(totalCost - discount).build();
    }

    private double getTotalCost(CourierPackage courierPackage, double baseDeliveryCost) {
        double totalCost = baseDeliveryCost + (courierPackage.getPackageWeight() * 10) + (courierPackage.getDeliveryDistance() * 5);
        return totalCost;
    }

    private double applyCoupon(Coupon coupon, double totalCost) {
        return (coupon.getDiscountPercentage() / (double) 100) * totalCost;
    }

    private Double roundTo2DecimalPlaces(Double val) {
        return Math.floor(val * 100) / 100;
    }
}
