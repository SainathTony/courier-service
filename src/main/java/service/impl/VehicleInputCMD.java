package service.impl;

import exception.CourierServiceException;
import lombok.RequiredArgsConstructor;
import model.VehicleInput;
import service.InputService;

import java.util.Scanner;

import static util.InputErrorMessages.INVALID_VEHICLE_INPUT;
import static util.InputErrorMessages.INVALID_VEHICLE_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.UNKNOWN_ERROR_MESSAGE;

@RequiredArgsConstructor
public class VehicleInputCMD implements InputService {

    private final Scanner scanner;

    @Override
    public VehicleInput readInputFromUser() {
        while(true) {
            try {
                System.out.print("Enter No_Of_Vehicles Max_Speed Max_Carry_Weight (space seperated): ");
                String[] vehicleRawInput = scanner.nextLine().split(" ");
                return transformInput(vehicleRawInput);
            } catch (CourierServiceException ex) {
                System.out.println(ex);
            } catch (NumberFormatException ex){
                System.out.println(INVALID_VEHICLE_INPUT);
            }catch (Exception e){
                System.out.println(UNKNOWN_ERROR_MESSAGE);
            }
        }
    }

    private VehicleInput transformInput(String[] vehicleInput) throws CourierServiceException {
        if(vehicleInput.length < 3) throw new CourierServiceException(INVALID_VEHICLE_INPUT_LENGTH_MESSAGE);
        int noOfVehicles = Integer.parseInt(vehicleInput[0]);
        int maxSpeed = Integer.parseInt(vehicleInput[1]);
        double maxCarryWeight = Double.parseDouble(vehicleInput[2]);
        return VehicleInput.builder().noOfVehicles(noOfVehicles).maxSpeed(maxSpeed).maxCarryWeight(maxCarryWeight).build();
    }
}
