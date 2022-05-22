package service;

import model.DeliverySummary;

import java.util.List;

public interface OutputService {
    void showResults(List<DeliverySummary> deliverySummary);
}
