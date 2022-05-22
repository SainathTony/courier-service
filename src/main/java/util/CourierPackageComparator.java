package util;

import model.CourierPackage;

import java.util.Comparator;

public class CourierPackageComparator implements Comparator<CourierPackage> {

    @Override
    public int compare(CourierPackage courier1, CourierPackage courier2) {
        return (int) (courier1.getPackageWeight() - courier2.getPackageWeight());
    }
}
