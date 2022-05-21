package service;

import model.CostEstimate;
import model.CourierPackage;

public interface CostEstimationService {
    CostEstimate estimate(CourierPackage courierPackage);
}
