package service.impl;

import model.CostEstimate;
import model.Coupon;
import model.CourierPackage;
import model.OfferCriteria;
import org.junit.jupiter.api.Test;
import service.CostEstimationService;
import service.CouponService;
import service.InputService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CostEstimationServiceImplTest {

    private final InputService inputService = mock(InputFromCommandLine.class);
    private final CouponService couponService = mock(CouponServiceImpl.class);
    private final CostEstimationService costEstimationService = new CostEstimationServiceImpl(inputService, couponService);

    @Test
    void shouldEstimateCostWithOfferForGivenPackage() {
        CostEstimate costEstimate = CostEstimate.builder().packageId("PKG1").discount(35).totalCost(665).build();
        OfferCriteria offerCriteria = new OfferByWeightAndDistance(50, 250, 10, 150);
        Coupon coupon = Coupon.builder().couponCode("OFR003").discountPercentage(5).offerCriteria(offerCriteria).build();
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build();
        when(couponService.getCouponByCouponCode("OFR003")).thenReturn(coupon);
        when(inputService.getBaseDeliveryCost()).thenReturn(100.0);

        CostEstimate estimate = costEstimationService.estimate(courierPackage);

        assertEquals(costEstimate, estimate);
    }

    @Test
    void shouldEstimateCostWithoutOfferForGivenPackage() {
        CostEstimate costEstimate = CostEstimate.builder().packageId("PKG1").discount(0).totalCost(175).build();
        OfferCriteria offerCriteria = new OfferByWeightAndDistance(0, 200, 70, 200);
        Coupon coupon = Coupon.builder().couponCode("OFR001").discountPercentage(10).offerCriteria(offerCriteria).build();
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build();
        when(couponService.getCouponByCouponCode("OFR001")).thenReturn(coupon);
        when(inputService.getBaseDeliveryCost()).thenReturn(100.0);

        CostEstimate estimate = costEstimationService.estimate(courierPackage);

        assertEquals(costEstimate, estimate);
    }
}