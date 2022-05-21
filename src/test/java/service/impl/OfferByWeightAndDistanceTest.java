package service.impl;

import model.CourierPackage;
import model.OfferCriteria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfferByWeightAndDistanceTest {

    private final OfferCriteria offerCriteria = new OfferByWeightAndDistance(0, 200, 70, 200);

    @Test
    void shouldReturnFalseForCoupon() {
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build();

        boolean couponApplicable = offerCriteria.isCouponApplicableFor(courierPackage);

        assertFalse(couponApplicable);
    }

    @Test
    void shouldReturnTrueForCoupon() {
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(80).deliveryDistance(100).couponCode("OFR003").build();

        boolean couponApplicable = offerCriteria.isCouponApplicableFor(courierPackage);

        assertTrue(couponApplicable);
    }
}