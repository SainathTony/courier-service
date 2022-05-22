package service.impl;

import model.CourierInput;
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
import static util.InputErrorMessages.INVALID_BASE_DELIVERY_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.INVALID_BASE_DELIVERY_OR_PACKAGE_COUNT_INPUT_MESSAGE;
import static util.InputErrorMessages.INVALID_COUPON_CODE_MESSAGE;
import static util.InputErrorMessages.INVALID_COURIER_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.INVALID_PACKAGE_COUNT_MESSAGE;
import static util.InputErrorMessages.INVALID_PACKAGE_ID_MESSAGE;
import static util.InputErrorMessages.INVALID_WEIGHT_OR_DISTANCE_MESSAGE;

class CourierInputCMDTest {

    private final Scanner scanner = mock(Scanner.class);
    private final InputService inputService = new CourierInputCMD(scanner);
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
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 3").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertEquals(estimateInput, courierInput);
    }

    @Test
    void shouldAskInputAgainIfBaseCostInputLengthIsInvalid() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 3 2").thenReturn("100 2").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertEquals(estimateInput, courierInput);
        assertTrue(outContent.toString().contains(INVALID_BASE_DELIVERY_INPUT_LENGTH_MESSAGE));
    }

    @Test
    void shouldAskInputAgainIfBaseCostInputIsInvalid() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 A").thenReturn("100 2").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertEquals(estimateInput, courierInput);
        assertTrue(outContent.toString().contains(INVALID_BASE_DELIVERY_OR_PACKAGE_COUNT_INPUT_MESSAGE));
    }

    @Test
    void shouldAskInputAgainIfPackageCountIsInvalid() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 0").thenReturn("100 2").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertEquals(estimateInput, courierInput);
        assertTrue(outContent.toString().contains(INVALID_PACKAGE_COUNT_MESSAGE));
    }

    @Test
    void shouldShowInvalidInputMessageWhenUserProvidesInvalidData() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 1").thenReturn("PKG1 5 OFR001").thenReturn("PKG1 5 5 OFR001");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_COURIER_INPUT_LENGTH_MESSAGE));
        assertEquals(estimateInput, courierInput);
    }

    @Test
    void shouldTakeInputAgainWhenUserProvidesInvalidData() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG1").packageWeight(5).deliveryDistance(5).couponCode("OFR001").build());
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 3").thenReturn("PKG1 5 OFR001").thenReturn("PKG1 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_COURIER_INPUT_LENGTH_MESSAGE));
        assertEquals(estimateInput, courierInput);
    }

    @Test
    void shouldShowInvalidPackageIdMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("A 5 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_PACKAGE_ID_MESSAGE));
        assertEquals(estimateInput, courierInput);
    }

    @Test
    void shouldShowInvalidCouponCodeMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("PKG1 5 5 A").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_COUPON_CODE_MESSAGE));
        assertEquals(estimateInput, courierInput);
    }

    @Test
    void shouldShowInvalidPackageWeightMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("PKG1 A 5 OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_WEIGHT_OR_DISTANCE_MESSAGE));
        assertEquals(estimateInput, courierInput);
    }

    @Test
    void shouldShowInvalidPackageDeliveryDistanceMessage() {
        List<CourierPackage> packageList = new ArrayList<>();
        packageList.add(CourierPackage.builder().packageId("PKG2").packageWeight(15).deliveryDistance(5).couponCode("OFR002").build());
        packageList.add(CourierPackage.builder().packageId("PKG3").packageWeight(10).deliveryDistance(100).couponCode("OFR003").build());
        CourierInput estimateInput = CourierInput.builder().baseDeliveryCost(100).courierPackageList(packageList).build();
        when(scanner.nextLine()).thenReturn("100 2").thenReturn("PKG1 5 A OFR001").thenReturn("PKG2 15 5 OFR002").thenReturn("PKG3 10 100 OFR003");

        CourierInput courierInput = (CourierInput) inputService.readInputFromUser();

        assertTrue(outContent.toString().contains(INVALID_WEIGHT_OR_DISTANCE_MESSAGE));
        assertEquals(estimateInput, courierInput);
    }
}