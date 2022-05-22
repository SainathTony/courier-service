package service.impl;

import model.DeliverySummary;
import service.OutputService;

import java.util.List;

public class ConsoleOutput implements OutputService {
    @Override
    public void showResults(List<DeliverySummary> deliverySummaryList) {
        deliverySummaryList.forEach(costEstimate -> System.out.println(costEstimate));
    }
}
