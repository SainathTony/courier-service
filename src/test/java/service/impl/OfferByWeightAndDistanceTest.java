package service.impl;

import model.CourierPackage;
import model.OfferCriteria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class OfferByWeightAndDistanceTest {

    private final OfferCriteria offerCriteria = new OfferByWeightAndDistance();

    @Test
    void shouldReturnFalseForCoupon() {
        CourierPackage courierPackage = CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build();

        boolean couponApplicable = offerCriteria.isCouponApplicableFor(courierPackage);

        assertFalse(couponApplicable);
    }
}