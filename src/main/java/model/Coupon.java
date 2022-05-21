package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
public class Coupon {
    private final String couponCode;
    private final int discountPercentage;
}
