package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CourierInput {
    private final double baseDeliveryCost;
    private final List<CourierPackage> courierPackageList;
}
