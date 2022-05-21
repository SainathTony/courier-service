package service.impl;

import model.VehicleInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InputService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static util.InputErrorMessages.INVALID_VEHICLE_INPUT;
import static util.InputErrorMessages.INVALID_VEHICLE_INPUT_LENGTH_MESSAGE;

class VehicleInputCMDTest {

    private final Scanner scanner = mock(Scanner.class);
    private final InputService inputService = new VehicleInputCMD(scanner);
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
        VehicleInput expectedVehicleInput = VehicleInput.builder().noOfVehicles(2).maxCarryWeight(100).maxSpeed(70).build();
        when(scanner.nextLine()).thenReturn("2 70 100");

        VehicleInput actualVehicleInput = (VehicleInput) inputService.readInputFromUser();

        assertEquals(expectedVehicleInput, actualVehicleInput);
    }

    @Test
    void shouldShowAllFieldsRequiredMessage() {
        VehicleInput expectedVehicleInput = VehicleInput.builder().noOfVehicles(2).maxCarryWeight(100).maxSpeed(70).build();
        when(scanner.nextLine()).thenReturn("70 100").thenReturn("2 70 100");

        VehicleInput actualVehicleInput = (VehicleInput) inputService.readInputFromUser();

        assertEquals(expectedVehicleInput, actualVehicleInput);
        assertTrue(outContent.toString().contains(INVALID_VEHICLE_INPUT_LENGTH_MESSAGE));
    }

    @Test
    void shouldShowInvalidInputMessage() {
        VehicleInput expectedVehicleInput = VehicleInput.builder().noOfVehicles(2).maxCarryWeight(100).maxSpeed(70).build();
        when(scanner.nextLine()).thenReturn("A 70 100").thenReturn("2 70 100");

        VehicleInput actualVehicleInput = (VehicleInput) inputService.readInputFromUser();

        assertEquals(expectedVehicleInput, actualVehicleInput);
        assertTrue(outContent.toString().contains(INVALID_VEHICLE_INPUT));
    }
}