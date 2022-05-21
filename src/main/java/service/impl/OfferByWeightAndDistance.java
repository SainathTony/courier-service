package service.impl;

import model.CourierPackage;
import model.OfferCriteria;

public class OfferByWeightAndDistance implements OfferCriteria {
    @Override
    public boolean isCouponApplicableFor(CourierPackage courierPackage) {
        return false;
    }
}
