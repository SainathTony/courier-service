package model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class DeliverySchedule {
    private final String packageId;
    private final double time;
}
