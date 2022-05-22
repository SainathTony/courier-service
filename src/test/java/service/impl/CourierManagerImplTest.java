package service.impl;

import model.CostEstimate;
import model.CourierInput;
import model.CourierPackage;
import model.VehicleInput;
import org.junit.jupiter.api.Test;
import service.CostEstimationService;
import service.DeliveryService;
import service.InputService;
import service.OutputService;
import service.CourierManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourierManagerImplTest {
    private final InputService courierInputService = mock(CourierInputCMD.class);
    private final InputService vehicleInputService = mock(VehicleInputCMD.class);
    private final OutputService outputService = mock(ConsoleOutput.class);
    private final CostEstimationService costEstimationService = mock(CostEstimationServiceImpl.class);
    private final DeliveryService deliveryService = mock(DeliveryService.class);
    private final CourierManager courierManager = new CourierManagerImpl(courierInputService, vehicleInputService, outputService, costEstimationService, deliveryService);

    @Test
    void shouldTakeInputAndEstimateCost() {
        List<CostEstimate> costEstimateList = new ArrayList<>();
        costEstimateList.add(CostEstimate.builder().packageId("PKG1").discount(0).totalCost(175).deliveryTime(3.98).build());
        costEstimateList.add(CostEstimate.builder().packageId("PKG2").discount(0).totalCost(275).deliveryTime(1.78).build());
        costEstimateList.add(CostEstimate.builder().packageId("PKG3").discount(35).totalCost(665).deliveryTime(1.42).build());
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        VehicleInput vehicleInput = VehicleInput.builder().noOfVehicles(2).maxCarryWeight(200).maxSpeed(70).build();
        when(courierInputService.readInputFromUser()).thenReturn(CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build());
        when(vehicleInputService.readInputFromUser()).thenReturn(vehicleInput);
        doNothing().when(outputService).showResults(costEstimateList);
        when(costEstimationService.estimate(any(CourierPackage.class), anyDouble())).thenReturn(costEstimateList.get(0))
                .thenReturn(costEstimateList.get(1)).thenReturn(costEstimateList.get(2));
        Map<String, Double> deliveryReport = new HashMap<>();
        deliveryReport.put("PKG1", 3.98);
        deliveryReport.put("PKG2", 1.78);
        deliveryReport.put("PKG3", 1.42);
        deliveryReport.put("PKG4", 0.85);
        deliveryReport.put("PKG5", 4.19);
        when(deliveryService.deliverPackages(vehicleInput)).thenReturn(deliveryReport);

        courierManager.manage();

        verify(courierInputService, times(1)).readInputFromUser();
        verify(costEstimationService, times(3)).estimate(any(CourierPackage.class), anyDouble());
        verify(costEstimationService, times(1)).estimate(packageList.get(0), 100);
        verify(costEstimationService, times(1)).estimate(packageList.get(1), 100);
        verify(costEstimationService, times(1)).estimate(packageList.get(2), 100);
        verify(outputService, times(1)).showResults(costEstimateList);
    }
}