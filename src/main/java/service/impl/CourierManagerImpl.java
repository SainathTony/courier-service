package service.impl;

import lombok.RequiredArgsConstructor;
import model.DeliverySummary;
import model.CourierInput;
import model.VehicleInput;
import service.CostEstimationService;
import service.DeliveryService;
import service.InputService;
import service.OutputService;
import service.CourierManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CourierManagerImpl implements CourierManager {
    private final InputService courierService;
    private final InputService vehicleInputService; 
    private final OutputService outputService;
    private final CostEstimationService costEstimationService;
    private final DeliveryService deliveryService;

    @Override
    public void manage() {
        CourierInput courierInput = (CourierInput) courierService.readInputFromUser();
        VehicleInput vehicleInput = (VehicleInput) vehicleInputService.readInputFromUser();
        List<DeliverySummary> deliveryInvoice = courierInput.getCourierPackageList().stream().map(courierPackage ->
                costEstimationService.getDeliveryCostWithOffer(courierPackage, courierInput.getBaseDeliveryCost())).collect(Collectors.toList());
        deliveryService.addPackages(courierInput.getCourierPackageList());
        Map<String, Double> deliveryReport = deliveryService.deliverPackages(vehicleInput);
        deliveryInvoice = deliveryInvoice.stream().map(costEstimate -> DeliverySummary.builder()
                .packageId(costEstimate.getPackageId())
                .discount(costEstimate.getDiscount())
                .totalCost(costEstimate.getTotalCost())
                .deliveryTime(deliveryReport.get(costEstimate.getPackageId())).build()).collect(Collectors.toList());
        outputService.showResults(deliveryInvoice);
    }
}
