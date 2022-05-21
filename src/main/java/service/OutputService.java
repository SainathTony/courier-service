package service;

import model.CostEstimate;

import java.util.List;

public interface OutputService {
    void showResults(List<CostEstimate> costEstimate);
}
