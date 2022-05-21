package service.impl;

import model.CourierPackage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InputService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static util.InputErrorMessages.INVALID_COUPON_CODE_MESSAGE;
import static util.InputErrorMessages.INVALID_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.INVALID_PACKAGE_ID_MESSAGE;
import static util.InputErrorMessages.INVALID_WEIGHT_OR_DISTANCE_MESSAGE;

class InputFromCommandLineTest {

    private final Scanner scanner = mock(Scanner.class);
    private final InputService inputService = new InputFromCommandLine(scanner);
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
    void shouldTakeInputFromUser() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 3").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInputFromUser();

        assertEquals(3, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }

    @Test
    void shouldShowInvalidInputMessageWhenUserProvidesInvalidData() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        when(scanner.nextLine()).thenReturn("100 1").thenReturn("PKG1 5 OFR001").thenReturn("PKG1 5 5 OFR001");

        inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_INPUT_LENGTH_MESSAGE));
        assertEquals(1, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }

    @Test
    void shouldTakeInputAgainWhenUserProvidesInvalidData() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 3").thenReturn("PKG1 5 OFR001").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_INPUT_LENGTH_MESSAGE));
        assertEquals(3, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }

    @Test
    void shouldShowInvalidPackageIdMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("A 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_PACKAGE_ID_MESSAGE));
        assertEquals(2, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }

    @Test
    void shouldShowInvalidCouponCodeMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("PKG1 5 5 A").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_COUPON_CODE_MESSAGE));
        assertEquals(2, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }

    @Test
    void shouldShowInvalidPackageWeightMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("PKG1 A 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_WEIGHT_OR_DISTANCE_MESSAGE));
        assertEquals(2, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }

    @Test
    void shouldShowInvalidPackageDeliveryDistanceMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("PKG1 5 A OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_WEIGHT_OR_DISTANCE_MESSAGE));
        assertEquals(2, inputService.getPackages().size());
        assertEquals(100, inputService.getBaseDeliveryCost());
        assertEquals(packageList, inputService.getPackages());
    }
}