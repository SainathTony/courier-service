package service.impl;

import model.CourierPackage;
import model.VehicleInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryVehicleManagerTest {

    private final VehicleManager vehicleManager = new VehicleManager();

    @BeforeEach
    public void setup() {
    }

    @Test
    void shouldReturnAvailableVehicle() {
        List<CourierPackage> firstDelivery = new ArrayList<>();
        firstDelivery.add(CourierPackage.builder().deliveryDistance(100).build());
        List<CourierPackage> secondDelivery = new ArrayList<>();
        secondDelivery.add(CourierPackage.builder().deliveryDistance(90).build());
        List<CourierPackage> thirdDelivery = new ArrayList<>();
        thirdDelivery.add(CourierPackage.builder().deliveryDistance(90).build());
        VehicleInput vehicleInput = VehicleInput.builder().noOfVehicles(2).maxCarryWeight(200).maxSpeed(70).build();

        vehicleManager.addVehicles(vehicleInput);
        int availableVehicleToDeliver1 = vehicleManager.getAvailableVehicleToDeliver();
        vehicleManager.deliverPackage(availableVehicleToDeliver1, firstDelivery);
        int availableVehicleToDeliver2 = vehicleManager.getAvailableVehicleToDeliver();
        vehicleManager.deliverPackage(availableVehicleToDeliver2, secondDelivery);
        int availableVehicleToDeliver3 = vehicleManager.getAvailableVehicleToDeliver();
        vehicleManager.deliverPackage(availableVehicleToDeliver3, thirdDelivery);

        assertEquals(0, availableVehicleToDeliver1);
        assertEquals(1, availableVehicleToDeliver2);
        assertEquals(1, availableVehicleToDeliver3);
    }

    @Test
    void shouldReturnDeliverySummary() {
        CourierPackage package1 = CourierPackage.builder().packageId("PKG1").packageWeight(50).deliveryDistance(30).couponCode("OFR001").build();
        CourierPackage package2 = CourierPackage.builder().packageId("PKG2").packageWeight(75).deliveryDistance(125).couponCode("OFFR0008").build();
        CourierPackage package3 = CourierPackage.builder().packageId("PKG3").packageWeight(175).deliveryDistance(100).couponCode("OFFR003").build();
        CourierPackage package4 = CourierPackage.builder().packageId("PKG4").packageWeight(110).deliveryDistance(60).couponCode("OFFR002").build();
        CourierPackage package5 = CourierPackage.builder().packageId("PKG5").packageWeight(155).deliveryDistance(95).couponCode("NA").build();
        Map<String, Double> expectedReport1 = new HashMap<>();
        expectedReport1.put("PKG2", 1.78);
        expectedReport1.put("PKG4", 0.85);
        Map<String, Double> expectedReport2 = new HashMap<>();
        expectedReport2.put("PKG3", 1.42);
        Map<String, Double> expectedReport3 = new HashMap<>();
        expectedReport3.put("PKG5", 4.18);
        Map<String, Double> expectedReport4 = new HashMap<>();
        expectedReport4.put("PKG1", 3.98);
        VehicleInput vehicleInput = VehicleInput.builder().noOfVehicles(2).maxCarryWeight(200).maxSpeed(70).build();

        vehicleManager.addVehicles(vehicleInput);
        Map<String, Double> actualReport1 = vehicleManager.deliverPackage(0, asList(package2, package4));
        Map<String, Double> actualReport2 = vehicleManager.deliverPackage(1, asList(package3));
        Map<String, Double> actualReport3 = vehicleManager.deliverPackage(1, asList(package5));
        Map<String, Double> actualReport4 = vehicleManager.deliverPackage(0, asList(package1));

        assertEquals(expectedReport1, actualReport1);
        assertEquals(expectedReport2, actualReport2);
        assertEquals(expectedReport3, actualReport3);
        assertEquals(expectedReport4, actualReport4);
    }
}