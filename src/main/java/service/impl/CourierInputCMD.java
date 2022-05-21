package service.impl;

import exception.CourierServiceException;
import lombok.RequiredArgsConstructor;
import model.CourierInput;
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
public class CourierInputCMD implements InputService {

    private final Scanner scanner;

    @Override
    public CourierInput readInputFromUser() {
        List<CourierPackage> packages = new ArrayList<>();
        System.out.print("Enter base delivery cost and number of packages (space seperated):");
        String[] baseDeliveryAndPackages = scanner.nextLine().split(" ");
        double baseDeliveryCost = Double.parseDouble(baseDeliveryAndPackages[0]);
        int numberOfPackages = Integer.parseInt(baseDeliveryAndPackages[1]);
        System.out.println("Enter Package_ID Package_Weight_In_KG Package_Delivery_Distance_In_KM Coupon_Code (Space seperated)");
        int counter = 0;
        while (counter < numberOfPackages) {
            try {
                System.out.print("Enter " + getPackageSNo(counter) + " package information: ");
                CourierPackage courierPackage = takePackageInput();
                packages.add(courierPackage);
                counter++;
            } catch (NumberFormatException ex) {
                System.out.println(INVALID_WEIGHT_OR_DISTANCE_MESSAGE);
            } catch (CourierServiceException ex) {
                System.out.println(ex);
            } catch (Exception ex) {
                System.out.println(UNKNOWN_ERROR_MESSAGE);
            }
        }
        return CourierInput.builder().baseDeliveryCost(baseDeliveryCost).courierPackageList(packages).build();
    }

    private String getPackageSNo(int counter) {
        if (counter == 0) return "1st";
        if (counter == 1) return "2nd";
        if (counter == 2) return "3rd";
        return (counter + 1) + "th";
    }

    private CourierPackage takePackageInput() throws CourierServiceException {
        String[] packageInfo = scanner.nextLine().split(" ");
        if (packageInfo.length < 4)
            throw new CourierServiceException(INVALID_INPUT_LENGTH_MESSAGE);
        String packageId = packageInfo[0];
        if (packageId == "" || packageId == null || packageId.length() < 2)
            throw new CourierServiceException(INVALID_PACKAGE_ID_MESSAGE);
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
}
