package service.impl;

import exception.CourierServiceException;
import lombok.RequiredArgsConstructor;
import model.CourierPackage;
import service.InputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static util.InputErrorMessages.INVALID_COUPON_CODE_MESSAGE;
import static util.InputErrorMessages.INVALID_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.INVALID_PACKAGE_ID_MESSAGE;
import static util.InputErrorMessages.INVALID_WEIGHT_OR_DISTANCE_MESSAGE;
import static util.InputErrorMessages.UNKNOWN_ERROR_MESSAGE;

@RequiredArgsConstructor
public class InputFromCommandLine implements InputService {

    private final Scanner scanner;
    private double BASE_DELIVERY_COST;
    private List<CourierPackage> packages = new ArrayList<>();

    @Override
    public void readInputFromUser() {
        System.out.print("Enter base delivery cost and number of packages (space seperated):");
        String[] baseDeliveryAndPackages = scanner.nextLine().split(" ");
        BASE_DELIVERY_COST = Integer.parseInt(baseDeliveryAndPackages[0]);
        int numberOfPackages = Integer.parseInt(baseDeliveryAndPackages[1]);
        System.out.println("Enter Package_ID Package_Weight_In_KG Package_Delivery_Distance_In_KM Coupon_Code (Space seperated)");
        while (numberOfPackages > 0) {
            try {
                System.out.print("Enter "+ getPackageSNo() +" package information: ");
                CourierPackage courierPackage = takePackageInput();
                packages.add(courierPackage);
                numberOfPackages -= 1;
            } catch (NumberFormatException ex) {
                System.out.println(INVALID_WEIGHT_OR_DISTANCE_MESSAGE);
            } catch (CourierServiceException ex) {
                System.out.println(ex);
            } catch (Exception ex) {
                System.out.println(UNKNOWN_ERROR_MESSAGE);
            }
        }
    }

    private String getPackageSNo() {
        int insertedPackages = packages.size();
        if (insertedPackages == 0) return "1st";
        if(insertedPackages == 1) return "2nd";
        if(insertedPackages == 2) return "3rd";
        return (insertedPackages + 1) + "th";
    }

    private CourierPackage takePackageInput() throws CourierServiceException {
        String[] packageInfo = scanner.nextLine().split(" ");
        if (packageInfo.length < 4)
            throw new CourierServiceException(INVALID_INPUT_LENGTH_MESSAGE);
        String packageId = packageInfo[0];
        if (packageId == "" || packageId == null || packageId.length() < 2) throw new CourierServiceException(INVALID_PACKAGE_ID_MESSAGE);
        double packageWeight = Double.parseDouble(packageInfo[1]);
        double packageDeliveryDistance = Double.parseDouble(packageInfo[2]);
        String couponCode = packageInfo[3];
        if (couponCode == "" || couponCode == null || couponCode.length() < 2)
            throw new CourierServiceException(INVALID_COUPON_CODE_MESSAGE);
        return CourierPackage.builder()
                .packageId(packageInfo[0])
                .packageWeight(packageWeight)
                .deliveryDistance(packageDeliveryDistance)
                .couponCode(couponCode).build();
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
