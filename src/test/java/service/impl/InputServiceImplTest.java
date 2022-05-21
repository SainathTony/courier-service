package service.impl;

import model.CourierPackage;
import org.junit.jupiter.api.Test;
import service.InputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputServiceImplTest {

    private final Scanner scanner = mock(Scanner.class);
    private final InputService inputService = new InputServiceImpl(scanner);

    @Test
    void shouldTakeInputFromUser() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 3").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInoutFromUser();

        assertEquals(3, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }
}