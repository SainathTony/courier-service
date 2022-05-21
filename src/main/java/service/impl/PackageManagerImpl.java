package service.impl;

import lombok.RequiredArgsConstructor;
import model.CostEstimate;
import model.CourierInput;
import service.CostEstimationService;
import service.InputService;
import service.OutputService;
import service.PackageManager;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PackageManagerImpl implements PackageManager {
    private final InputService inputService;
    private final OutputService outputService;
    private final CostEstimationService costEstimationService;

    @Override
    public void manage() {
        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();
        List<CostEstimate> estimates = courierInput.getCourierPackageList().stream().map(courierPackage ->
                costEstimationService.estimate(courierPackage, courierInput.getBaseDeliveryCost())).collect(Collectors.toList());
        outputService.showResults(estimates);
    }
}
