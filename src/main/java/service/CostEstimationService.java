package service;

import model.DeliverySummary;
import model.CourierPackage;

public interface CostEstimationService {
    DeliverySummary getDeliveryCostWithOffer(CourierPackage courierPackage, double baseDeliveryCost);
}
