package service.impl;

import model.CostEstimate;
import model.CourierPackage;
import org.junit.jupiter.api.Test;
import service.CostEstimationService;
import service.InputService;
import service.OutputService;
import service.PackageManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PackageManagerImplTest {
    private final InputService inputService = mock(InputFromCommandLine.class);
    private final OutputService outputService = mock(ConsoleOutput.class);
    private final CostEstimationService costEstimationService = mock(CostEstimationServiceImpl.class);
    private final PackageManager packageManager = new PackageManagerImpl(inputService, outputService, costEstimationService);

    @Test
    void shouldTakeInputAndEstimateCost() {
        List<CostEstimate> costEstimateList = new ArrayList<>();
        costEstimateList.add(CostEstimate.builder().packageId("PKG1").discount(0).totalCost(175).build());
        costEstimateList.add(CostEstimate.builder().packageId("PKG2").discount(0).totalCost(275).build());
        costEstimateList.add(CostEstimate.builder().packageId("PKG3").discount(35).totalCost(665).build());
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        doNothing().when(inputService).readInputFromUser();
        when(inputService.getPackages()).thenReturn(packageList);
        doNothing().when(outputService).showResults(costEstimateList);
        when(costEstimationService.estimate(any(CourierPackage.class))).thenReturn(costEstimateList.get(0))
                .thenReturn(costEstimateList.get(1)).thenReturn(costEstimateList.get(2));

        packageManager.manage();

        verify(inputService, times(1)).readInputFromUser();
        verify(inputService, times(1)).getPackages();
        verify(costEstimationService, times(3)).estimate(any(CourierPackage.class));
        verify(costEstimationService, times(1)).estimate(packageList.get(0));
        verify(costEstimationService, times(1)).estimate(packageList.get(1));
        verify(costEstimationService, times(1)).estimate(packageList.get(2));
        verify(outputService, times(1)).showResults(costEstimateList);
    }
}