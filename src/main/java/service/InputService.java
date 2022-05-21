package service;

import model.CourierPackage;

import java.util.List;

public interface InputService {
    void readInoutFromUser();

    double getBaseDeliveryCost();

    List<CourierPackage> getPackages();
}
