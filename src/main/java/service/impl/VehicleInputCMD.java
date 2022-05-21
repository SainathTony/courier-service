package service.impl;

import lombok.RequiredArgsConstructor;
import model.VehicleInput;
import service.InputService;

import java.util.Scanner;

@RequiredArgsConstructor
public class VehicleInputCMD implements InputService {

    private final Scanner scanner;

    @Override
    public VehicleInput readInputFromUser() {
        System.out.print("Enter No_Of_Vehicles Max_Speed Max_Carriable_Weight (space seperated): ");
        String[] vehicleInput = scanner.nextLine().split(" ");
        int noOfVehicles = Integer.parseInt(vehicleInput[0]);
        int maxSpeed = Integer.parseInt(vehicleInput[1]);
        double maxCarryWeight = Double.parseDouble(vehicleInput[2]);
        return VehicleInput.builder().noOfVehicles(noOfVehicles).maxSpeed(maxSpeed).maxCarryWeight(maxCarryWeight).build();
    }
}
