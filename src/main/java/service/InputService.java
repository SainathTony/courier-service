package service;

import model.CourierPackage;

import java.util.List;

public interface InputService {
    void readInputFromUser();

    double getBaseDeliveryCost();

    List<CourierPackage> getPackages();
}
