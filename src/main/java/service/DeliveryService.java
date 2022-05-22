package service;

import model.CourierPackage;
import model.VehicleInput;

import java.util.List;
import java.util.Map;

public interface DeliveryService {
    Map<String, Double> deliverPackages(VehicleInput vehicleInput);

    void addPackages(List<CourierPackage> courierPackageList);
}
