package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class CostEstimate {
    private final String packageId;
    private final double discount;
    private final double totalCost;
}
