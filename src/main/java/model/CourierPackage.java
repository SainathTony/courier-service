package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class CourierPackage {
    private String packageId;
    private double packageWeight;
    private double deliveryDistance;
    private String couponCode;
}
