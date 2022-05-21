package service.impl;

import lombok.RequiredArgsConstructor;
import model.CourierPackage;
import model.OfferCriteria;

@RequiredArgsConstructor
public class OfferByWeightAndDistance implements OfferCriteria {

    private final int minimumDistance;
    private final int maximumDistance;
    private final int minimumWeight;
    private final int maximumWeight;

    @Override
    public boolean isCouponApplicableFor(CourierPackage courierPackage) {
        return courierPackage.getPackageWeight() >= minimumWeight && courierPackage.getPackageWeight() <= maximumWeight &&
                courierPackage.getDeliveryDistance() >= minimumDistance && courierPackage.getDeliveryDistance() <= maximumDistance;
    }
}
