package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import offers.OfferCriteria;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Coupon {
    private final String couponCode;
    private final int discountPercentage;
    private final OfferCriteria offerCriteria;
}
