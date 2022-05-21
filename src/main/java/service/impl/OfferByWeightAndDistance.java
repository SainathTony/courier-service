package service.impl;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import model.CourierPackage;
import model.OfferCriteria;

@RequiredArgsConstructor
@EqualsAndHashCode
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
