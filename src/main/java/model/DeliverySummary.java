package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class DeliverySummary {
    private final String packageId;
    private final double discount;
    private final double totalCost;
    private final double deliveryTime;

    @Override
    public String toString() {
        return packageId + " " + discount + " " + totalCost + " " + deliveryTime;
    }
}
