package service.impl;

import lombok.RequiredArgsConstructor;
import model.CourierPackage;
import service.InputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class InputServiceImpl implements InputService {

    private final Scanner scanner;
    private double BASE_DELIVERY_COST;
    private List<CourierPackage> packages = new ArrayList<>();

    @Override
    public void readInoutFromUser() {
        String[] baseDeliveryAndPackages = scanner.nextLine().split(" ");
        BASE_DELIVERY_COST = Integer.parseInt(baseDeliveryAndPackages[0]);
        int numberOfPackages = Integer.parseInt(baseDeliveryAndPackages[1]);
        for (int i = 0; i < numberOfPackages; i++) {
            String[] packageInfo = scanner.nextLine().split(" ");
            packages.add(CourierPackage.builder().packageId(packageInfo[0])
                    .packageWeight(Integer.parseInt(packageInfo[1]))
                    .deliveryDistance(Integer.parseInt(packageInfo[2]))
                    .couponCode(packageInfo[3])
                    .build());
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
