package service.impl;

import lombok.RequiredArgsConstructor;
import model.CourierPackage;
import model.VehicleInput;
import service.DeliveryService;
import util.CourierPackageComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final VehicleManager vehicleManager;
    private final List<CourierPackage> courierPackageList = new ArrayList<>();
    private final Map<String, Boolean> packageStatusMapper = new HashMap<>();

    @Override
    public Map<String, Double> deliverPackages(VehicleInput vehicleInput) {
        Map<String, Double> packageDeliverySummary = new HashMap<>();
        while (packagesAreAvailable()) {
            int availableVehicleToDeliver = vehicleManager.getAvailableVehicleToDeliver();
            List<CourierPackage> availablePackagesForDelivery = getAvailablePackages();
            List<CourierPackage> deliverablePackages = getPackagesForDelivery(availablePackagesForDelivery, vehicleInput.getMaxCarryWeight());
            Map<String, Double> deliverySummary = vehicleManager.deliverPackage(availableVehicleToDeliver, deliverablePackages);
            deliverablePackages.forEach(courierPackage -> packageDeliverySummary.put(courierPackage.getPackageId(), deliverySummary.get(courierPackage.getPackageId())));
            updateDeliveredPackages(deliverablePackages);
        }
        return packageDeliverySummary;
    }

    @Override
    public void addPackages(List<CourierPackage> courierPackageList) {
        this.courierPackageList.addAll(courierPackageList);
        courierPackageList.forEach(courierPackage -> packageStatusMapper.put(courierPackage.getPackageId(), true));
    }

    private void updateDeliveredPackages(List<CourierPackage> deliverablePackages) {
        deliverablePackages.forEach(courierPackage -> packageStatusMapper.put(courierPackage.getPackageId(), false));
    }

    private List<CourierPackage> getAvailablePackages() {
        return courierPackageList.stream().filter(courierPackage -> packageStatusMapper.get(courierPackage.getPackageId()) == true).collect(Collectors.toList());
    }

    private boolean packagesAreAvailable() {
        for (CourierPackage courierPackage : courierPackageList) {
            if (packageStatusMapper.get(courierPackage.getPackageId()) == true) return true;
        }
        return false;
    }

    private List<CourierPackage> getPackagesForDelivery(List<CourierPackage> courierPackageList, double maxWeight) {
        Collections.sort(courierPackageList, new CourierPackageComparator());
        double loadWeight = 0;
        List<CourierPackage> load = new ArrayList<>();
        for (CourierPackage courierPackage : courierPackageList) {
            load.add(courierPackage);
            loadWeight += courierPackage.getPackageWeight();
            if (loadWeight > maxWeight) {
                double weightDifference = loadWeight - maxWeight;
                CourierPackage packageCloseToLoadDifference = getPackageCloseToLoadDifference(load, weightDifference);
                load.remove(packageCloseToLoadDifference);
                loadWeight -= packageCloseToLoadDifference.getPackageWeight();
            }
        }
        ;
        return load;
    }

    private CourierPackage getPackageCloseToLoadDifference(List<CourierPackage> load, double weightDifference) {
        CourierPackage packageCloseToExtraWeight = load.get(0);
        double loadDifference = abs(weightDifference - packageCloseToExtraWeight.getPackageWeight());
        for (CourierPackage courierPackage : load) {
            if (abs(weightDifference - courierPackage.getPackageWeight()) == loadDifference) {
                if (packageCloseToExtraWeight.getDeliveryDistance() < courierPackage.getDeliveryDistance()) {
                    packageCloseToExtraWeight = courierPackage;
                }
            }
            if (abs(weightDifference - courierPackage.getPackageWeight()) < loadDifference) {
                packageCloseToExtraWeight = courierPackage;
            }
            loadDifference = abs(weightDifference - packageCloseToExtraWeight.getPackageWeight());
        }
        return packageCloseToExtraWeight;
    }

    List<CourierPackage> oldLogic(List<CourierPackage> courierPackageList, double maxWeight) {
        Collections.sort(courierPackageList, new CourierPackageComparator());
        double packageMaxWeight = courierPackageList.get(0).getPackageWeight();
        int numberOfPackages = 1;
        List<CourierPackage> load = courierPackageList.subList(0, 1);
        int i = 0;
        int j = 1;
        while (i != j && j < courierPackageList.size()) {
            List<CourierPackage> currentLoad = courierPackageList.subList(i, j + 1);
            double sumTill = currentLoad.stream().mapToDouble(weight -> weight.getPackageWeight()).sum();
            if (packageMaxWeight <= sumTill && numberOfPackages <= (j - i) && sumTill <= maxWeight) {
                if (packageMaxWeight == sumTill) {
                    double savedPackageDeliveryTime = load.stream().mapToDouble(courier -> courier.getDeliveryDistance()).sum();
                    double newPackageDeliveryTime = currentLoad.stream().mapToDouble(courier -> courier.getDeliveryDistance()).sum();
                    if (savedPackageDeliveryTime > newPackageDeliveryTime) {
                        packageMaxWeight = sumTill;
                        load = currentLoad;
                    }
                } else {
                    packageMaxWeight = sumTill;
                    load = currentLoad;
                }
            }
            if (sumTill > maxWeight) i++;
            else j++;
        }
        return load;
    }
}
