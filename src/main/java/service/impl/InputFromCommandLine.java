package service.impl;

import lombok.RequiredArgsConstructor;
import model.CourierPackage;
import service.InputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class InputFromCommandLine implements InputService {

    private final Scanner scanner;
    private double BASE_DELIVERY_COST;
    private List<CourierPackage> packages = new ArrayList<>();

    @Override
    public void readInputFromUser() {
        String[] baseDeliveryAndPackages = scanner.nextLine().split(" ");
        BASE_DELIVERY_COST = Integer.parseInt(baseDeliveryAndPackages[0]);
        int numberOfPackages = Integer.parseInt(baseDeliveryAndPackages[1]);
        while (numberOfPackages > 0) {
            try{
                String[] packageInfo = scanner.nextLine().split(" ");
                String packageId = packageInfo[0];
                Double packageWeight = Double.parseDouble(packageInfo[1]);
                Double deliveryDistance = Double.parseDouble(packageInfo[2]);
                String couponCode = packageInfo[3];
                packages.add(CourierPackage.builder().packageId(packageId)
                        .packageWeight(packageWeight)
                        .deliveryDistance(deliveryDistance)
                        .couponCode(couponCode)
                        .build());
                numberOfPackages -= 1;
            } catch (NumberFormatException ex){
                System.out.println("Please provide valid input. Format: PackageId Package_Weight_In_KG Delivery_Distance_IN_KM Offer_Code");
            }
        }
    }

    @Override
    public double getBaseDeliveryCost() {
        return BASE_DELIVERY_COST;
    }

    @Override
    public List<CourierPackage> getPackages() {
        return packages;
    }
}
