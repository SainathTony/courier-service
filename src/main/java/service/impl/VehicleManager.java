package service.impl;

import model.CourierPackage;
import model.VehicleInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleManager {
    private VehicleInput vehicleInput;
    private Map<Integer, Double> vehicleStatusMapper = new HashMap<>();

    public void addVehicles(VehicleInput vehicleInput) {
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

    public Map<String, Double> deliverPackage(int vehicleSNo, List<CourierPackage> courierPackages) {
        double existingDeliveryTime = vehicleStatusMapper.get(vehicleSNo);
        Map<String, Double> packageDeliveryMapper = new HashMap<>();
        double maxDeliveryTime = roundTo2DecimalPlaces(courierPackages.get(0).getDeliveryDistance() / vehicleInput.getMaxSpeed());
        for (CourierPackage courierPackage : courierPackages) {
            double packageDeliveryTime = roundTo2DecimalPlaces(courierPackage.getDeliveryDistance() / vehicleInput.getMaxSpeed());
            if (maxDeliveryTime < packageDeliveryTime)
                maxDeliveryTime = packageDeliveryTime;
            packageDeliveryMapper.put(courierPackage.getPackageId(), roundTo2DecimalPlaces((existingDeliveryTime + packageDeliveryTime)));
        }
        vehicleStatusMapper.put(vehicleSNo, existingDeliveryTime + roundTo2DecimalPlaces((maxDeliveryTime * 2)));
        return packageDeliveryMapper;
    }

    private Double roundTo2DecimalPlaces(Double val) {
        return Math.floor(val * 100) / 100;
    }

}
