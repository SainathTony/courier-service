package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
public class CostEstimate {
    private final String packageId;
    private final double discount;
    private final double totalCost;

    @Override
    public String toString() {
        return packageId + " " + discount + " " + totalCost;
    }
}
