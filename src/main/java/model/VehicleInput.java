package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
public class VehicleInput {
    private final int noOfVehicles;
    private final int maxSpeed;
    private final double maxCarryWeight;
}
