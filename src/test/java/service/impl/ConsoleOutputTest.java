package service.impl;

import model.DeliverySummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OutputService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleOutputTest {

    private final OutputService outputService = new ConsoleOutput();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setupStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(new PrintStream(System.out));
    }

    @Test
    void shouldPrintResultsInConsole() {
        List<DeliverySummary> deliverySummaryList = new ArrayList<>();
        deliverySummaryList.add(DeliverySummary.builder().packageId("PKG1").discount(0).totalCost(175).deliveryTime(1.58).build());
        deliverySummaryList.add(DeliverySummary.builder().packageId("PKG2").discount(0).totalCost(275).deliveryTime(2.6).build());
        deliverySummaryList.add(DeliverySummary.builder().packageId("PKG3").discount(35).totalCost(665).deliveryTime(5.7).build());
        String expectedOutput = "PKG1 0.0 175.0 1.58\nPKG2 0.0 275.0 2.6\nPKG3 35.0 665.0 5.7\n";
        outputService.showResults(deliverySummaryList);

        assertEquals(expectedOutput, outContent.toString());
    }
}