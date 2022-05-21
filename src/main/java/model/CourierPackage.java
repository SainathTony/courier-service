package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class CourierPackage {
    private String packageId;
    private int packageWeight;
    private int deliveryDistance;
    private String couponCode;
}
