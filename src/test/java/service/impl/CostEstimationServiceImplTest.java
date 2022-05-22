package service.impl;

import model.DeliverySummary;
import model.CourierInput;
import model.Coupon;
import model.CourierPackage;
import offers.impl.OfferByWeightAndDistance;
import offers.OfferCriteria;
import org.junit.jupiter.api.Test;
import service.CostEstimationService;
import service.CouponService;
import service.InputService;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CostEstimationServiceImplTest {

    private final InputService inputService = mock(CourierInputCMD.class);
    private final CouponService couponService = mock(CouponServiceImpl.class);
    private final CostEstimationService costEstimationService = new CostEstimationServiceImpl(couponService);

    @Test
    void shouldEstimateCostWithOfferForGivenPackage() {
        DeliverySummary deliverySummary = DeliverySummary.builder().packageId("PKG1").discount(35).totalCost(665).build();
        OfferCriteria offerCriteria = new OfferByWeightAndDistance(50, 250, 10, 150);
        Coupon coupon = Coupon.builder().couponCode("OFR003").discountPercentage(5).offerCriteria(offerCriteria).build();
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build();
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(asList(courierPackage)).build();
        when(couponService.getCouponByCouponCode("OFR003")).thenReturn(coupon);
        when(inputService.readInputFromUser()).thenReturn(estimateInput);

        DeliverySummary estimate = costEstimationService.getDeliveryCostWithOffer(courierPackage, 100);

        assertEquals(deliverySummary, estimate);
    }

    @Test
    void shouldEstimateCostWithoutOfferForGivenPackage() {
        DeliverySummary deliverySummary = DeliverySummary.builder().packageId("PKG1").discount(0).totalCost(175).build();
        OfferCriteria offerCriteria = new OfferByWeightAndDistance(0, 200, 70, 200);
        Coupon coupon = Coupon.builder().couponCode("OFR001").discountPercentage(10).offerCriteria(offerCriteria).build();
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build();
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(asList(courierPackage)).build();
        when(couponService.getCouponByCouponCode("OFR001")).thenReturn(coupon);
        when(inputService.readInputFromUser()).thenReturn(estimateInput);

        DeliverySummary estimate = costEstimationService.getDeliveryCostWithOffer(courierPackage, 100);

        assertEquals(deliverySummary, estimate);
    }
}