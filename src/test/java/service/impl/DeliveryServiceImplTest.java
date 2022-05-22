package service.impl;

import model.CourierPackage;
import model.VehicleInput;
import org.junit.jupiter.api.Test;
import service.DeliveryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeliveryServiceImplTest {

    private final VehicleManager vehicleManager = mock(VehicleManager.class);
    private final DeliveryService deliveryService = new DeliveryServiceImpl(vehicleManager);

    @Test
    void shouldReturnDeliverSummary() {
        List<CourierPackage> packageList = new ArrayList<>();
        CourierPackage package1 = CourierPackage.builder().packageId("PKG1").packageWeight(50).deliveryDistance(30).couponCode("OFR001").build();
        CourierPackage package2 = CourierPackage.builder().packageId("PKG2").packageWeight(75).deliveryDistance(125).couponCode("OFFR0008").build();
        CourierPackage package3 = CourierPackage.builder().packageId("PKG3").packageWeight(175).deliveryDistance(100).couponCode("OFFR003").build();
        CourierPackage package4 = CourierPackage.builder().packageId("PKG4").packageWeight(110).deliveryDistance(60).couponCode("OFFR002").build();
        CourierPackage package5 = CourierPackage.builder().packageId("PKG5").packageWeight(155).deliveryDistance(95).couponCode("NA").build();
        packageList.add(package1);
        packageList.add(package2);
        packageList.add(package3);
        packageList.add(package4);
        packageList.add(package5);
        VehicleInput vehicleInput = VehicleInput.builder().noOfVehicles(2).maxSpeed(70).maxCarryWeight(200).build();
        Map<String, Double> expectedDeliverySchedule = new HashMap<>();
        expectedDeliverySchedule.put("PKG1", 3.98);
        expectedDeliverySchedule.put("PKG2", 1.78);
        expectedDeliverySchedule.put("PKG3", 1.42);
        expectedDeliverySchedule.put("PKG4", 0.85);
        expectedDeliverySchedule.put("PKG5", 4.19);
        Map<String, Double> deliveryReport1 = new HashMap<>();
        deliveryReport1.put("PKG2", 1.78);
        deliveryReport1.put("PKG4", 0.85);
        Map<String, Double> deliveryReport2 = new HashMap<>();
        deliveryReport2.put("PKG3", 1.42);
        Map<String, Double> deliveryReport3 = new HashMap<>();
        deliveryReport3.put("PKG5", 4.19);
        Map<String, Double> deliveryReport4 = new HashMap<>();
        deliveryReport4.put("PKG1", 3.98);
        when(vehicleManager.getAvailableVehicleToDeliver()).thenReturn(0).thenReturn(1).thenReturn(1).thenReturn(0);
        when(vehicleManager.deliverPackage(0, asList(package2, package4))).thenReturn(deliveryReport1);
        when(vehicleManager.deliverPackage(1, asList(package3))).thenReturn(deliveryReport2);
        when(vehicleManager.deliverPackage(1, asList(package5))).thenReturn(deliveryReport3);
        when(vehicleManager.deliverPackage(0, asList(package1))).thenReturn(deliveryReport4);

        deliveryService.addPackages(packageList);
        Map<String, Double> actualDeliverySchedule = deliveryService.deliverPackages(vehicleInput);

        assertEquals(expectedDeliverySchedule, actualDeliverySchedule);
    }
}