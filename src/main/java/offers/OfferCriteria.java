package offers;

import model.CourierPackage;

public interface OfferCriteria {
    boolean isCouponApplicableFor(CourierPackage courierPackage);
}
