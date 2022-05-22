package service.impl;

import exception.CourierServiceException;
import lombok.RequiredArgsConstructor;
import model.CourierInput;
import model.CourierPackage;
import service.InputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static util.InputErrorMessages.INVALID_BASE_DELIVERY_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.INVALID_BASE_DELIVERY_OR_PACKAGE_COUNT_INPUT_MESSAGE;
import static util.InputErrorMessages.INVALID_COUPON_CODE_MESSAGE;
import static util.InputErrorMessages.INVALID_COURIER_INPUT_LENGTH_MESSAGE;
import static util.InputErrorMessages.INVALID_PACKAGE_COUNT_MESSAGE;
import static util.InputErrorMessages.INVALID_PACKAGE_ID_MESSAGE;
import static util.InputErrorMessages.INVALID_WEIGHT_OR_DISTANCE_MESSAGE;
import static util.InputErrorMessages.UNKNOWN_ERROR_MESSAGE;

@RequiredArgsConstructor
public class CourierInputCMD implements InputService {

    private final Scanner scanner;

    @Override
    public CourierInput readInputFromUser() {
        List<Integer> baseDeliverAndPackageCount = takeInputForDeliveryCostPackageCount();
        double baseDeliveryCost = baseDeliverAndPackageCount.get(0);
        int numberOfPackages = baseDeliverAndPackageCount.get(1);
        List<CourierPackage> packages = takeInputForCourierPackages(numberOfPackages);
        return CourierInput.builder().baseDeliveryCost(baseDeliveryCost).courierPackageList(packages).build();
    }

    private List<Integer> takeInputForDeliveryCostPackageCount() {
        int baseDeliveryCost;
        int numberOfPackages;
        while (true) {
            System.out.print("Enter base delivery cost and number of packages (space seperated):");
            try {
                String[] baseDeliveryAndPackages = scanner.nextLine().split(" ");
                if (baseDeliveryAndPackages.length != 2)
                    throw new CourierServiceException(INVALID_BASE_DELIVERY_INPUT_LENGTH_MESSAGE);
                baseDeliveryCost = Integer.parseInt(baseDeliveryAndPackages[0]);
                numberOfPackages = Integer.parseInt(baseDeliveryAndPackages[1]);
                if (numberOfPackages < 1) throw new CourierServiceException(INVALID_PACKAGE_COUNT_MESSAGE);
                break;
            } catch (NumberFormatException ex) {
                System.out.println(INVALID_BASE_DELIVERY_OR_PACKAGE_COUNT_INPUT_MESSAGE);
            } catch (CourierServiceException ex) {
                System.out.println(ex);
            } catch (Exception ex) {
                System.out.println(UNKNOWN_ERROR_MESSAGE);
            }
        }
        return asList(baseDeliveryCost, numberOfPackages);
    }

    private List<CourierPackage> takeInputForCourierPackages(int numberOfPackages) {
        List<CourierPackage> packages = new ArrayList<>();
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
        return packages;
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
            throw new CourierServiceException(INVALID_COURIER_INPUT_LENGTH_MESSAGE);
        String packageId = packageInfo[0];
        if (packageId == null || packageId.length() < 2) throw new CourierServiceException(INVALID_PACKAGE_ID_MESSAGE);
        double packageWeight = Double.parseDouble(packageInfo[1]);
        double packageDeliveryDistance = Double.parseDouble(packageInfo[2]);
        String couponCode = packageInfo[3];
        if (couponCode == null || couponCode.length() < 2)
            throw new CourierServiceException(INVALID_COUPON_CODE_MESSAGE);
        return CourierPackage.builder()
                .packageId(packageInfo[0])
                .packageWeight(packageWeight)
                .deliveryDistance(packageDeliveryDistance)
                .couponCode(couponCode).build();
    }
}
