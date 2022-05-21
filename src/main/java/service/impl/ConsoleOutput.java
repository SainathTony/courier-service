package service.impl;

import model.CostEstimate;
import service.OutputService;

import java.util.List;

public class ConsoleOutput implements OutputService {
    @Override
    public void showResults(List<CostEstimate> costEstimateList) {
        costEstimateList.forEach(costEstimate -> System.out.println(costEstimate));
    }
}
