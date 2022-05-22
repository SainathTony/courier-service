package service.impl;

import model.CourierPackage;
import model.VehicleInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleManager {
    private final VehicleInput vehicleInput;
    private Map<Integer, Double> vehicleStatusMapper = new HashMap<>();

    VehicleManager(VehicleInput vehicleInput) {
        this.vehicleInput = vehicleInput;
        for (int vehicle = 0; vehicle < vehicleInput.getNoOfVehicles(); vehicle++) {
            vehicleStatusMapper.put(vehicle, 0.0);
        }
    }

    public int getAvailableVehicleToDeliver() {
        int availableVehicleSNo = 0;
        double minimumDeliveryTime = vehicleStatusMapper.get(availableVehicleSNo);
        for (Map.Entry<Integer, Double> vehicleStatus : vehicleStatusMapper.entrySet()) {
            if (vehicleStatus.getValue() < minimumDeliveryTime) {
                minimumDeliveryTime = vehicleStatus.getValue();
                availableVehicleSNo = vehicleStatus.getKey();
            }
        }
        return availableVehicleSNo;
    }

    public void deliverPackage(int vehicleSNo, List<CourierPackage> courierPackages) {
        double existingDeliveryTime = vehicleStatusMapper.get(vehicleSNo);
        double maxDeliveryTime = courierPackages.get(0).getDeliveryDistance() / vehicleInput.getMaxSpeed();
        for (CourierPackage courierPackage : courierPackages) {
            double tempDeliveryTime = courierPackage.getDeliveryDistance() / vehicleInput.getMaxSpeed();
            if (maxDeliveryTime < tempDeliveryTime)
                maxDeliveryTime = tempDeliveryTime;
        }
        vehicleStatusMapper.put(vehicleSNo, existingDeliveryTime + (maxDeliveryTime * 2));
    }
}
