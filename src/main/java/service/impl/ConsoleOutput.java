package service.impl;

import model.DeliverySummary;
import service.OutputService;

import java.util.List;

public class ConsoleOutput implements OutputService {
    @Override
    public void showResults(List<DeliverySummary> deliverySummaryList) {
        System.out.println("\n====== Delivery Summary ======");
        deliverySummaryList.forEach(deliverySummary -> System.out.println(deliverySummary));
    }
}
