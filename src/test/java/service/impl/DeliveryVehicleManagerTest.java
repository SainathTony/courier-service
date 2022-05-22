package service.impl;

import model.CourierPackage;
import model.VehicleInput;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryVehicleManagerTest {

    private final VehicleInput vehicleInput = new VehicleInput(2, 70, 200);
    private final VehicleManager vehicleManager = new VehicleManager(vehicleInput);

    @Test
    void shouldReturnAvailableVehicle() {
        List<CourierPackage> firstDelivery = new ArrayList<>();
        firstDelivery.add(CourierPackage.builder().deliveryDistance(100).build());
        List<CourierPackage> secondDelivery = new ArrayList<>();
        secondDelivery.add(CourierPackage.builder().deliveryDistance(90).build());
        List<CourierPackage> thirdDelivery = new ArrayList<>();
        thirdDelivery.add(CourierPackage.builder().deliveryDistance(90).build());

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
}